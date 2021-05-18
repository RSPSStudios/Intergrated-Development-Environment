package com.javatar.plugin.definition.editor.ui.rsmenu.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

class ActionEditorModel : ViewModel() {
    val targetName = bind { SimpleStringProperty(this, "target_name") }
    val actions = bind { SimpleListProperty<String>(this, "actions", FXCollections.observableArrayList()) }
    val selected = bind { SimpleStringProperty(this, "selected") }
    val maxOptions = bind { SimpleIntegerProperty(this, "max_options", 5) }
}