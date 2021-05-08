package com.javatar.plugin.definition.editor.ui.selectors.models

import com.javatar.osrs.definitions.impl.ItemDefinition
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

class ItemSelectModel : ViewModel() {

    val items = bind { SimpleListProperty<ItemDefinition>(this, "items", FXCollections.observableArrayList()) }

    val selected = bind { SimpleObjectProperty<ItemDefinition>(this, "selected") }

    val searchText = bind { SimpleStringProperty(this, "search_text", "") }

}