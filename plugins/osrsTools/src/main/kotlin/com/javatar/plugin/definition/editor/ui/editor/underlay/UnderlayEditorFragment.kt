package com.javatar.plugin.definition.editor.ui.editor.underlay

import com.javatar.api.http.Client
import com.javatar.api.http.StringBody
import com.javatar.api.ui.models.AccountModel
import com.javatar.osrs.definitions.impl.UnderlayDefinition
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.OsrsDefinitionEditor.Companion.gson
import com.javatar.plugin.definition.editor.ui.editor.underlay.model.UnderlayModel
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.paint.Color
import javafx.util.Duration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.javafx.JavaFx
import tornadofx.*

class UnderlayEditorFragment : Fragment("Underlay Editor") {

    val underlayModel: UnderlayModel by inject()

    val underlays = OldSchoolDefinitionManager.underlays

    val client: Client by di()
    val account: AccountModel by di()

    init {
        underlayModel.cache.onChange {
            if (it != null) {
                loadUnderlays()
            }
        }
    }
    override val root = vbox {
        style = "-fx-base: #3f474f;"
        toolbar(
            button("Pack Underlay") {
                disableWhen(underlayModel.underlay.isNull)
                action {
                    val creds = account.activeCredentials.get()
                    val underlay = underlayModel.underlay.get()
                    if (creds != null && underlay != null) {
                        underlayModel.commitUnderlay()
                        val json = gson.toJson(underlay)
                        client.post<ByteArray>("tools/osrs/underlays", StringBody(json), creds)
                            .catch {
                                alert(Alert.AlertType.ERROR, "Error Packing Underlay", it.message)
                                emit(byteArrayOf())
                            }
                            .onEach {
                                if (it.isNotEmpty()) {
                                    val cache = underlayModel.cache.get()
                                    if (cache != null) {
                                        cache.put(2, 1, underlay.id, it)
                                        cache.index(2).update()
                                        alert(
                                            Alert.AlertType.INFORMATION,
                                            "Packing Underlay",
                                            "Successfully packed underlay."
                                        )
                                    }
                                }
                            }
                            .launchIn(CoroutineScope(Dispatchers.JavaFx))
                    }

                }
            },
            button("Add Underlay") {
                action {
                    val underlay = UnderlayDefinition()
                    underlay.id = underlays.nextId
                    underlays.add(underlay)
                    underlayModel.underlays.add(underlay)
                }
            },
            button("Remove Underlay") {
                disableWhen(underlayModel.underlay.isNull)
                action {
                    val cache = underlayModel.cache.get()
                    if (cache != null) {
                        val underlay = underlayModel.underlay.get()
                        underlayModel.underlays.remove(underlay)
                        underlays.remove(underlay)
                        cache.remove(2, 1, underlay.id)
                        cache.index(2).update()
                    }
                }
            })
        hbox {
            spacing = 10.0
            prefWidth = 520.0
            listview(underlayModel.underlays) {
                underlayModel.underlay.bind(selectionModel.selectedItemProperty())
                cellFormat {
                    text = "Underlay ${it.id}"
                }
            }
            vbox {
                spacing = 25.0
                alignment = Pos.CENTER
                rectangle(0, 0, 255, 255) {
                    fill = Color.TRANSPARENT
                    underlayModel.underlay.onChange {
                        if (underlayModel.color.get() != null) {
                            animateFill(Duration(2000.0), fill as Color, underlayModel.color.get())
                        }
                    }
                    underlayModel.color.onChange {
                        if (it != null) {
                            animateFill(Duration(2000.0), fill as Color, it)
                        }
                    }
                }
                colorpicker(underlayModel.color)
            }

        }
    }

    private fun loadUnderlays() {
        val cache = underlayModel.cache.get()
        if (cache != null) {
            val underlayIds = cache.index(2).archive(1)?.fileIds() ?: intArrayOf()
            val list = mutableListOf<UnderlayDefinition>()
            for (underlayId in underlayIds) {
                val data = cache.data(2, 1, underlayId)
                if (data != null) {
                    val def = underlays.load(underlayId, data)
                    list.add(def)
                }
            }
            underlayModel.underlays.setAll(list)
        }
    }

}