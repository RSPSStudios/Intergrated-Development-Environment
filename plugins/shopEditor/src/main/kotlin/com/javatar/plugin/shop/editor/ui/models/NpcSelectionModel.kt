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

class NpcSelectionModel : ViewModel() {

    val npcs = bind { SimpleListProperty<Int>(this, "npc_ids", FXCollections.observableArrayList()) }

    val filteredNpcs = bind { SimpleListProperty<Int>(this, "npc_filtered_ids", FXCollections.observableArrayList()) }

    val searchText = bind { SimpleStringProperty(this, "search_text", "") }

    val result = bind { SimpleIntegerProperty(this, "result", -1) }

    val selectedNpc = bind { SimpleIntegerProperty(this, "selected_npc", -1) }

}
