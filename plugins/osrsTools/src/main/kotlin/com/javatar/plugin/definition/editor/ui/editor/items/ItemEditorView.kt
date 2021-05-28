package com.javatar.plugin.definition.editor.ui.editor.items

import com.displee.cache.CacheLibrary
import com.javatar.api.http.Client
import com.javatar.api.http.StringBody
import com.javatar.api.ui.loadPluginFXML
import com.javatar.api.ui.models.AccountModel
import com.javatar.api.ui.utilities.contextmenu
import com.javatar.osrs.definitions.impl.ItemDefinition
import com.javatar.osrs.tools.ItemGeneration
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.OsrsDefinitionEditor
import com.javatar.plugin.definition.editor.OsrsDefinitionEditor.Companion.gson
import com.javatar.plugin.definition.editor.ui.editor.items.model.ItemDefinitionModel
import com.javatar.plugin.definition.editor.ui.editor.items.wizard.NewItemWizard
import javafx.fxml.FXML
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.javafx.JavaFx
import tornadofx.*
import java.util.*

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

    private val client: Client by di()
    private val account: AccountModel by di()

    init {
        duplicateItem.disableWhen(itemList.selectionModel.selectedItemProperty().isNull)
        deleteItem.disableWhen(itemList.selectionModel.selectedItemProperty().isNull
            .or(definitionModel.placeHolderTemplateId.isNotEqualTo(-1).or(definitionModel.notedTemplate.isNotEqualTo(-1))))
        packItem.disableWhen(itemList.selectionModel.selectedItemProperty().isNull)
        itemPropertiesPane.disableWhen(itemList.selectionModel.selectedItemProperty().isNull)

        itemList.cellFormat {
            contextmenu {
                item("Generate Bank Note"){
                    disableWhen(definitionModel.notedTemplate.isNotEqualTo(-1)
                        .or(definitionModel.placeHolderTemplateId.isNotEqualTo(-1)
                            .or(definitionModel.notedID.isNotEqualTo(-1))))
                }.action {
                    val noteDef = ItemGeneration.generateBankNoteItem(item, manager.nextId)
                    manager.add(noteDef)
                    manager.modifiedDefinitions[noteDef.id] = noteDef.id
                    itemList.items.add(noteDef)
                    itemList.selectionModel.clearSelection()
                    itemList.selectionModel.select(item)
                }
                item("Generate Placeholder"){
                    disableWhen(definitionModel.notedTemplate.isNotEqualTo(-1)
                        .or(definitionModel.placeHolderTemplateId.isNotEqualTo(-1)
                            .or(definitionModel.placeholderId.isNotEqualTo(-1))))
                }.action {
                    val placeDef = ItemGeneration.generatePlaceholderItem(item, manager.nextId)
                    manager.add(placeDef)
                    manager.modifiedDefinitions[placeDef.id] = placeDef.id
                    itemList.items.add(placeDef)
                    itemList.selectionModel.clearSelection()
                    itemList.selectionModel.select(item)
                }
            }
            graphic = hbox {
                spacing = 15.0
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
                vbox {
                    spacing = 10.0
                    label(item.name)
                    label("Item ID: ${item.id}")
                }
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
                val newDef = ItemDefinition(manager.nextId)
                newDef.name = def.name.get()
                newDef.cost = def.cost.get()
                newDef.isMembers = def.members.get()
                newDef.isTradeable = def.tradeable.get()
                newDef.inventoryModel = def.inventoryModel.get()
                manager.add(newDef)
                manager.modifiedDefinitions[newDef.id] = newDef.id
                itemList.items.add(newDef)
                if(def.generateBankNote.get()) {
                    val noteDef = ItemGeneration.generateBankNoteItem(newDef, manager.nextId)
                    manager.add(noteDef)
                    manager.modifiedDefinitions[noteDef.id] = noteDef.id
                    itemList.items.add(noteDef)
                }
                if(def.generatePlaceholder.get()) {
                    val placeDef = ItemGeneration.generatePlaceholderItem(newDef, manager.nextId)
                    manager.add(placeDef)
                    manager.modifiedDefinitions[placeDef.id] = placeDef.id
                    itemList.items.add(placeDef)
                }
                itemList.scrollTo(newDef)
                itemList.selectionModel.select(newDef)
            }
            openModal()
        }
    }

    @FXML
    fun duplicateItem() {
        val selected = itemList.selectionModel.selectedItem
        searchField.clear()
        if(selected != null) {
            val copiedDef = definitionModel.createItem(manager.nextId)
            manager.add(copiedDef)
            manager.modifiedDefinitions[copiedDef.id] = copiedDef.id
            itemList.items.add(copiedDef)
            if(definitionModel.notedID.get() != -1) {
                val noteDef = ItemGeneration.generateBankNoteItem(copiedDef, manager.nextId)
                manager.add(noteDef)
                manager.modifiedDefinitions[noteDef.id] = noteDef.id
                itemList.items.add(noteDef)
            }
            if(definitionModel.placeholderId.get() != -1) {
                val placeDef = ItemGeneration.generatePlaceholderItem(copiedDef, manager.nextId)
                manager.add(placeDef)
                manager.modifiedDefinitions[placeDef.id] = placeDef.id
                itemList.items.add(placeDef)
            }
            itemList.scrollTo(copiedDef)
            itemList.selectionModel.select(copiedDef)
        }
    }

    @FXML
    fun packItem() {
        val creds = account.activeCredentials.get()
        val cache = definitionModel.cacheProperty.get()
        if (creds != null && cache != null) {

            val ndef = definitionModel.createItem()
            manager.add(ndef)
            manager.modifiedDefinitions[ndef.id] = ndef.id

            manager.modifiedDefinitions.values.forEach { defId ->
                val def = manager[defId]
                if(def != null) {
                    val json = gson.toJson(def)
                    client.post<ByteArray>("tools/osrs/items", StringBody(json), creds)
                        .catch {
                            alert(Alert.AlertType.ERROR, "Error Encoding", it.message)
                            emit(byteArrayOf())
                        }
                        .onEach {
                            if(it.isNotEmpty()) {
                                cache.put(2, 10, defId, it)
                                cache.index(2).update()
                            }
                        }
                        .onCompletion {
                            alert(Alert.AlertType.INFORMATION, "Encode Request", "Finished Encoding ${manager.modifiedDefinitions.size} item definitions.")
                            manager.modifiedDefinitions.clear()
                        }
                        .launchIn(CoroutineScope(Dispatchers.JavaFx))
                } else {
                    alert(Alert.AlertType.ERROR, "No Item Definition found", "Item Definition $defId not found for encoding.")
                }
            }
        } else if(creds == null) {
            alert(Alert.AlertType.ERROR, "Not Logged in", "Please login before requesting an encoding.")
        } else {
            println("WTF")
        }
    }

    @FXML
    fun deleteItem() {
        val item = itemList.selectedItem
        val cache = definitionModel.cacheProperty.get()
        if(item != null && cache != null) {
            val confirm = alert(
                Alert.AlertType.CONFIRMATION,
                "Delete ${item.name} - ${item.id}",
                "Are you sure you want to delete ${item.name}?",
                buttons = arrayOf(ButtonType.CANCEL, ButtonType.YES)
            )
            if(confirm.result == ButtonType.YES) {
                if(item.notedID != -1) {
                    val notedDef = manager[item.notedID]
                    manager.remove(notedDef)
                    itemList.items.remove(notedDef)
                    if (notedDef != null) {
                        cache.remove(2, 10, notedDef.id)
                    }
                }
                if(item.placeholderId != -1) {
                    val placeDef = manager[item.placeholderId]
                    manager.remove(placeDef)
                    itemList.items.remove(placeDef)
                    if(placeDef != null) {
                        cache.remove(2, 10, placeDef.id)
                    }
                }
                manager.remove(item)
                itemList.items.remove(item)
                itemList.selectionModel.clearAndSelect(0)
                cache.remove(2, 10, item.id)
                cache.index(2).update()
            }
        }
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
                it.name.lowercase(Locale.getDefault()).contains(result.lowercase(Locale.getDefault()))
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