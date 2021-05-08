package com.javatar.plugin.definition.editor.ui.editor.inventories.model

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.impl.InventoryDefinition
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel
import tornadofx.onChange

class InventoryEditorModel : ViewModel() {

    val inventories = bind { SimpleListProperty<InventoryDefinition>(this, "invs", FXCollections.observableArrayList()) }

    val selected = bind { SimpleObjectProperty<InventoryDefinition>(this, "selected") }

    val cache = SimpleObjectProperty<CacheLibrary>(this, "cache")

    val invSize = bind { SimpleIntegerProperty(this, "inv_size", 0) }

    init {
        selected.onChange {
            if(it != null) {
                invSize.set(it.size)
            }
        }
    }

    fun commitInventory() {
        val inv = selected.get()
        if(inv != null) {
            inv.size = invSize.get()
        }
    }

}