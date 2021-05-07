package com.javatar.plugin.definition.editor.ui.editor.overlay.model

import com.displee.cache.CacheLibrary
import com.javatar.api.ui.toColor
import com.javatar.api.ui.toRS2
import com.javatar.osrs.definitions.impl.OverlayDefinition
import com.javatar.plugin.definition.editor.managers.SpriteProvider
import com.javatar.plugin.definition.editor.managers.TextureProvider
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.scene.paint.Color
import tornadofx.ViewModel
import tornadofx.onChange

class OverlayEditorModel : ViewModel() {

    val overlays = bind { SimpleListProperty<OverlayDefinition>(this, "overlays", FXCollections.observableArrayList()) }

    val textureId = bind { SimpleIntegerProperty(this, "texture_id", -1) }
    val color = bind { SimpleObjectProperty<Color>(this, "color") }
    val secondColor = bind { SimpleObjectProperty<Color>(this, "second_color") }
    val hidesUnderlay = bind { SimpleBooleanProperty(this, "hides_underlay", false) }

    val overlay = SimpleObjectProperty<OverlayDefinition>(this, "overlay")

    val cache = SimpleObjectProperty<CacheLibrary>(this, "cache")

    val textureProvider = SimpleObjectProperty<TextureProvider>(this, "texture_provider")

    val spriteProvider = SimpleObjectProperty<SpriteProvider>(this, "sprite_provider")

    init {
        overlay.onChange {
            if(it != null) {
                textureId.set(it.texture)
                color.set(it.rgbColor.toColor())
                secondColor.set(it.secondaryRgbColor.toColor())
                hidesUnderlay.set(it.isHideUnderlay)
            }
        }
    }

    fun commitOverlay() : OverlayDefinition {
        val o = overlay.get()
        o.isHideUnderlay = hidesUnderlay.get()
        o.rgbColor = color.get().toRS2()
        o.secondaryRgbColor = secondColor.get().toRS2()
        o.texture = textureId.get()
        return overlay.get()
    }

}