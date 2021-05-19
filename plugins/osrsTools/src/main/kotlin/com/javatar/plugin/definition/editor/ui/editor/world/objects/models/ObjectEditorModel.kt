package com.javatar.plugin.definition.editor.ui.editor.world.objects.models

import com.javatar.osrs.definitions.impl.ObjectDefinition
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

class ObjectEditorModel : ViewModel() {

    val objects = bind { SimpleListProperty<ObjectDefinition>(this, "objects", FXCollections.observableArrayList()) }
    val selected = bind { SimpleObjectProperty<ObjectDefinition>(this, "selected") }

    fun update(def: ObjectDefinition) {

    }

}