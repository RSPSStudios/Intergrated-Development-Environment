package com.javatar.plugin.definition.editor.ui.editor.underlay.model

import com.displee.cache.CacheLibrary
import com.javatar.api.ui.toColor
import com.javatar.api.ui.toRS2
import com.javatar.osrs.definitions.impl.UnderlayDefinition
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.paint.Color
import tornadofx.ViewModel
import tornadofx.onChange

class UnderlayModel : ViewModel() {

    val underlays = bind { SimpleListProperty<UnderlayDefinition>(this, "underlays") }

    val underlay = bind { SimpleObjectProperty<UnderlayDefinition>(this, "underlay") }

    val color = bind { SimpleObjectProperty<Color>(this, "color") }

    val cache = SimpleObjectProperty<CacheLibrary>(this, "cache")

    init {
        underlay.onChange {
            if (it != null) {
                color.set(it.color.toColor())
            }
        }
    }

    fun commitUnderlay() {
        val u = underlay.get()
        if (u != null) {
            u.color = color.get().toRS2()
        }
    }

}