package com.javatar.plugin.definition.editor.ui.editor.cvars.model

import com.javatar.osrs.definitions.impl.ClientVarDefinition
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel
import tornadofx.onChange

class ClientPreferencesModel : ViewModel() {

    val preferences = bind { SimpleListProperty<ClientVarDefinition>(this, "preferences", FXCollections.observableArrayList()) }

    val selected = bind { SimpleObjectProperty<ClientVarDefinition>(this, "selected") }

    val id = bind { SimpleIntegerProperty(this, "id", -1) }

    val persistent = bind { SimpleBooleanProperty(this, "persistent", false) }

    init {
        selected.onChange {
            if(it != null) {
                id.set(it.id)
                persistent.set(it.persisent)
            }
        }
    }

    fun commitPreference() {
        val pref = selected.get()
        pref.persisent = persistent.get()
    }

}