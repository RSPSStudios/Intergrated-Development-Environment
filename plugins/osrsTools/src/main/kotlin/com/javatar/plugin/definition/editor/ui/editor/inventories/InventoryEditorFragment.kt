package com.javatar.plugin.definition.editor.ui.editor.inventories

import com.javatar.api.http.Client
import com.javatar.api.http.StringBody
import com.javatar.api.ui.models.AccountModel
import com.javatar.osrs.definitions.impl.InventoryDefinition
import com.javatar.osrs.definitions.loaders.InventoryLoader
import com.javatar.plugin.definition.editor.OsrsDefinitionEditor.Companion.gson
import com.javatar.plugin.definition.editor.managers.ConfigDefinitionManager
import com.javatar.plugin.definition.editor.ui.editor.inventories.model.InventoryEditorModel
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.javafx.JavaFx
import tornadofx.*

class InventoryEditorFragment : Fragment("Inventory Editor") {

    val invModel: InventoryEditorModel by inject()

    val account: AccountModel by di()
    val client: Client by di()

    val invs = ConfigDefinitionManager(InventoryLoader())

    init {
        invModel.cache.onChange {
            if (it != null) {
                loadInventories()
            }
        }
    }

    override val root = vbox {
        style = "-fx-base: #3f474f;"
        toolbar(
            button("Pack Inventory") {
                disableWhen(invModel.selected.isNull)
                action {
                    val cache = invModel.cache.get()
                    val creds = account.activeCredentials.get()
                    if (cache != null && creds != null) {
                        invModel.commitInventory()
                        val inv = invModel.selected.get()
                        val json = gson.toJson(inv)
                        client.post<ByteArray>("tools/osrs/inventories", StringBody(json), creds)
                            .catch {
                                alert(Alert.AlertType.ERROR, "Error Packing Inventory", it.message)
                                emit(byteArrayOf())
                            }
                            .onEach {
                                if (it.isNotEmpty()) {
                                    cache.put(2, 5, inv.id, it)
                                    cache.index(2).update()
                                    alert(
                                        Alert.AlertType.INFORMATION,
                                        "Packing Inventory",
                                        "Successfully packed inventory definition."
                                    )
                                }
                            }.launchIn(CoroutineScope(Dispatchers.JavaFx))
                    }
                }
            },
            button("Add Inventory") {
                action {
                    val inv = InventoryDefinition()
                    inv.id = invs.nextId
                    invs.add(inv)
                    invModel.inventories.add(inv)
                }
            },
            button("Remove Inventory") {
                disableWhen(invModel.selected.isNull)
                action {
                    val confirm = alert(
                        Alert.AlertType.CONFIRMATION,
                        "Delete Inventory",
                        "Are you sure you want to delete this inventory definition?",
                        buttons = arrayOf(ButtonType.YES, ButtonType.NO)
                    )
                    if (confirm.result === ButtonType.YES) {
                        val cache = invModel.cache.get()
                        if (cache != null) {
                            val inv = invModel.selected.get()
                            invs.remove(inv)
                            invModel.inventories.remove(inv)
                            cache.remove(2, 5, inv.id)
                            cache.index(2).update()
                        }
                    }
                }
            }
        )
        hbox {
            spacing = 10.0
            listview(invModel.inventories) {
                invModel.selected.bind(selectionModel.selectedItemProperty())
                cellFormat {
                    text = "Inventory ${it.id}"
                }
            }
            form {
                spacing = 10.0
                alignment = Pos.CENTER
                fieldset("Configs") {
                    field("Inventory Size") {
                        spinner(property = invModel.invSize, editable = true, min = 0, max = Int.MAX_VALUE) {
                            editor.stripNonNumeric()
                        }
                    }
                }
            }
        }
    }

    private fun loadInventories() {
        val cache = invModel.cache.get()
        if (cache != null) {
            val invIds = cache.index(2).archive(5)?.fileIds() ?: intArrayOf()
            val list = mutableListOf<InventoryDefinition>()
            for (invId in invIds) {
                val data = cache.data(2, 5, invId)
                if (data != null) {
                    list.add(invs.load(invId, data))
                }
            }
            invModel.inventories.setAll(list)
        }
    }

}