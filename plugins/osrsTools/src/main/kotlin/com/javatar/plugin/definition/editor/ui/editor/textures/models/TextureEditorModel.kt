package com.javatar.plugin.definition.editor.ui.editor.textures.models

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.impl.TextureDefinition
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel
import tornadofx.onChange

class TextureEditorModel : ViewModel() {

    val textures = bind { SimpleListProperty<TextureDefinition>(this, "textures", FXCollections.observableArrayList()) }

    val selected = bind { SimpleObjectProperty<TextureDefinition>(this, "selected") }

    val cache = SimpleObjectProperty<CacheLibrary>(this, "cache")

    val spriteIds = bind { SimpleListProperty<Int>(this, "sprite_ids", FXCollections.observableArrayList()) }

    val animationDirection = bind { SimpleIntegerProperty(this, "animation_direction", 0) }

    val animationSpeed = bind { SimpleIntegerProperty(this, "animation_speed", 0) }

    init {
        selected.onChange {
            if(it != null) {
                spriteIds.setAll(it.fileIds.toList())
                animationDirection.set(it.animationDirection)
                animationSpeed.set(it.animationSpeed)
            }
        }
    }

    fun commitTexture() {
        val tex = selected.get()
        if(tex != null) {
            tex.fileIds = spriteIds.toIntArray()
            tex.animationDirection = animationDirection.get()
            tex.animationSpeed = animationSpeed.get()
        }
    }

}