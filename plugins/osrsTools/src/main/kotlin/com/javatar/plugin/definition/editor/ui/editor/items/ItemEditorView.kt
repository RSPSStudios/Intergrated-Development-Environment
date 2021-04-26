package com.javatar.plugin.definition.editor.ui.editor.items

import com.javatar.api.ui.loadPluginFXML
import com.javatar.osrs.definitions.impl.ItemDefinition
import com.javatar.plugin.definition.editor.OsrsDefinitionEditor
import com.javatar.plugin.definition.editor.ui.editor.items.model.ItemDefinitionModel
import javafx.fxml.FXML
import javafx.scene.control.ListView
import javafx.scene.control.MenuItem
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment
import tornadofx.disableWhen

class ItemEditorView : Fragment("Old School Item Editor") {

    override val root: AnchorPane = loadPluginFXML("${OsrsDefinitionEditor.properties["pluginPath"]}/itemeditor.fxml")

    val duplicateItem: MenuItem by fxid()
    val deleteItem: MenuItem by fxid()

    val itemList: ListView<ItemDefinition> by fxid()

    val itemConfigPane: AnchorPane by fxid()

    val definitionModel: ItemDefinitionModel by inject()

    init {
        duplicateItem.disableWhen(itemList.selectionModel.selectedItemProperty().isNull)
        deleteItem.disableWhen(itemList.selectionModel.selectedItemProperty().isNull)
        itemConfigPane.add<ItemConfigsFragment>()
    }

    @FXML
    fun newItem() {

    }

    @FXML
    fun duplicateItem() {

    }

    @FXML
    fun packItem() {

    }

    @FXML
    fun deleteItem() {

    }



}