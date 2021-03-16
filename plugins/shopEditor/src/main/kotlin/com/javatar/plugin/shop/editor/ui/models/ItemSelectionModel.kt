package com.javatar.plugin.shop.editor.ui.models

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 15 2021
 */

class ItemSelectionModel : ViewModel() {

    val items = bind { SimpleListProperty<Int>(this, "items", FXCollections.observableArrayList()) }

    val filteredItems = bind { SimpleListProperty<Int>(this, "filtered_items", FXCollections.observableArrayList()) }

    val result = bind { SimpleIntegerProperty(this, "result", -1) }

    val searchText = bind { SimpleStringProperty(this, "search_text", "") }

    val selectedItem = bind { SimpleIntegerProperty(this, "selected_item", -1) }

}
