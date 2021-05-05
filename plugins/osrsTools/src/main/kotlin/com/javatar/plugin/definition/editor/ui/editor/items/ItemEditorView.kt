package com.javatar.plugin.definition.editor.ui.editor.items

import com.displee.cache.CacheLibrary
import com.javatar.api.ui.loadPluginFXML
import com.javatar.osrs.definitions.impl.ItemDefinition
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.OsrsDefinitionEditor
import com.javatar.plugin.definition.editor.ui.editor.items.model.ItemDefinitionModel
import com.javatar.plugin.definition.editor.ui.editor.items.wizard.NewItemWizard
import javafx.fxml.FXML
import javafx.geometry.Pos
import javafx.scene.control.Accordion
import javafx.scene.control.ListView
import javafx.scene.control.MenuItem
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import tornadofx.*

class ItemEditorView : Fragment("Old School Item Editor") {

    override val root: AnchorPane = loadPluginFXML("${OsrsDefinitionEditor.properties["pluginPath"]}/itemeditor.fxml")

    private val searchField: TextField by fxid()

    private val duplicateItem: MenuItem by fxid()
    private val deleteItem: MenuItem by fxid()
    private val packItem: MenuItem by fxid()

    private val itemPropertiesPane: Accordion by fxid()

    private val itemList: ListView<ItemDefinition> by fxid()

    private val itemConfigPane: AnchorPane by fxid()
    private val itemModelPane: AnchorPane by fxid()
    private val itemOptionsPane: AnchorPane by fxid()
    private val itemInventoryPane: AnchorPane by fxid()

    val definitionModel: ItemDefinitionModel by inject()

    private val manager = OldSchoolDefinitionManager.items

    init {
        duplicateItem.disableWhen(itemList.selectionModel.selectedItemProperty().isNull)
        deleteItem.disableWhen(itemList.selectionModel.selectedItemProperty().isNull)
        packItem.disableWhen(itemList.selectionModel.selectedItemProperty().isNull)
        itemPropertiesPane.disableWhen(itemList.selectionModel.selectedItemProperty().isNull)

        itemList.cellFormat {
            graphic = hbox {
                spacing = 15.0
                alignment = Pos.CENTER
                val factory = definitionModel.itemSpriteFactory.get()
                if (factory != null) {
                    val image = factory.toFXImage(
                        item.id,
                        1,
                        1,
                        3153952,
                        false
                    )
                    imageview(image)
                }
                label(item.name)
            }
        }

        itemList.selectionModel.selectedItemProperty().onChange {
            if(it != null) {
                definitionModel.update(it)
                definitionModel.id.set(it.id)
            }
        }

        itemConfigPane.add<ItemConfigsFragment>()
        itemModelPane.add<ItemModelFragment>()
        itemOptionsPane.add<ItemOptionsFragment>()
        itemInventoryPane.add<ItemInventoryFragment>()

        definitionModel.cacheProperty.addListener { _, _, new ->
            loadItems(new)
        }

        searchField.textProperty().onChange {
            if(it != null && it.isEmpty() && definitionModel.cacheProperty.get() != null) {
                loadItems(definitionModel.cacheProperty.get())
            }
        }
    }

    private fun loadItems(cache: CacheLibrary) {
        if(itemList.items.isNotEmpty()) {
            itemList.items.clear()
        }
        val ids = cache.index(2).archive(10)?.fileIds() ?: intArrayOf()
        ids.forEach {
            val data = cache.data(2, 10, it)
            if(data != null) {
                itemList.items.add(manager.load(it, data))
            }
        }
    }

    @FXML
    fun newItem() {
        find<NewItemWizard> {
            onComplete {



                println("New Item! ${def.name}")
                println("Cost: ${def.cost}")
            }
            openModal()
        }
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

    @FXML
    fun searchItems() {
        val result = searchField.text
        if(result.isInt()) {
            itemList.items.clear()
            val itemId = result.toInt()
            val def = manager[itemId]
            if (def != null) {
                itemList.items.addAll(def, *findRelatedItems(def).toTypedArray())
            }
        } else {
            val filtered = manager.definitions.values.filter {
                it.name.toLowerCase().contains(result.toLowerCase())
            }
            itemList.items.setAll(filtered)
        }
    }

    private fun findRelatedItems(item: ItemDefinition) : List<ItemDefinition> {
        val cache = definitionModel.cacheProperty.get()
        val list = mutableListOf<ItemDefinition>()
        if(cache != null) {
            if(item.notedID != -1) {
                val notedItem = manager[item.notedID]
                if(notedItem == null) {
                    val data = cache.data(2, 10, item.notedID)
                    if (data != null) {
                        list.add(manager.load(item.notedID, data))
                    }
                } else list.add(notedItem)
            }
            if(item.placeholderId != -1) {
                val placeItem = manager[item.placeholderId]
                if(placeItem == null) {
                    val data = cache.data(2, 10, item.placeholderId)
                    if(data != null) {
                        list.add(manager.load(item.placeholderId, data))
                    }
                } else list.add(placeItem)
            }
            if(item.boughtId != -1) {
                val boughtItem = manager[item.boughtId]
                if(boughtItem == null) {
                    val data = cache.data(2, 10, item.boughtId)
                    if(data != null) {
                        list.add(manager.load(item.boughtId, data))
                    }
                } else list.add(boughtItem)
            }
        }
        return list
    }

}