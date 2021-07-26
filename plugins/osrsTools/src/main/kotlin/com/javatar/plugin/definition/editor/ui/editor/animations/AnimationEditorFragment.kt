package com.javatar.plugin.definition.editor.ui.editor.animations

import com.javatar.api.http.Client
import com.javatar.api.http.StringBody
import com.javatar.api.ui.models.AccountModel
import com.javatar.osrs.definitions.impl.ItemDefinition
import com.javatar.osrs.definitions.impl.SequenceDefinition
import com.javatar.osrs.definitions.loaders.ItemLoader
import com.javatar.osrs.definitions.loaders.SequenceLoader
import com.javatar.plugin.definition.editor.OsrsDefinitionEditor.Companion.gson
import com.javatar.plugin.definition.editor.managers.ConfigDefinitionManager
import com.javatar.plugin.definition.editor.ui.editor.animations.model.AnimationEditorModel
import com.javatar.plugin.definition.editor.ui.selectors.SelectItemFragment
import com.javatar.plugin.definition.editor.ui.selectors.scope.ItemSelectScope
import javafx.beans.binding.Bindings
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

class AnimationEditorFragment : Fragment("Animation Editor") {

    val animModel: AnimationEditorModel by inject()

    val anims = ConfigDefinitionManager(SequenceLoader())
    val items = ConfigDefinitionManager(ItemLoader())

    val accountModel: AccountModel by di()
    val client: Client by di()

    init {
        animModel.cache.onChange {
            if (it != null) {
                loadAnimations()
            }
        }
    }

    override val root = vbox {
        style = "-fx-base: #3f474f;"
        toolbar(
            button("Pack Animation") {
                disableWhen(animModel.selected.isNull)
                action {
                    val cache = animModel.cache.get()
                    val creds = accountModel.activeCredentials.get()
                    if (cache != null && creds != null) {
                        animModel.commitAnimation()
                        val anim = animModel.selected.get()
                        if (anim != null) {
                            val json = gson.toJson(anim)
                            client.post<ByteArray>("tools/osrs/anims", StringBody(json), creds)
                                .catch {
                                    alert(Alert.AlertType.ERROR, "Error Packing Animation", it.message)
                                    emit(byteArrayOf())
                                }
                                .onEach {
                                    if (it.isNotEmpty()) {
                                        cache.put(2, 12, anim.id, it)
                                        cache.index(2).update()
                                        alert(
                                            Alert.AlertType.INFORMATION,
                                            "Packing Animation",
                                            "Successfully packed animation."
                                        )
                                    }
                                }.launchIn(CoroutineScope(Dispatchers.JavaFx))
                        }
                    }
                }
            },
            button("Add Animation") {
                action {
                    alert(
                        Alert.AlertType.WARNING,
                        "Animation Creation not supported",
                        "Please duplicate an existing animation."
                    )
                }
            },
            button("Duplicate Animation") {
                disableWhen(animModel.selected.isNull)
                action {
                    val anim = animModel.selected.get()
                    val copy = SequenceDefinition(anims.nextId)
                    copy.rightHandItem = anim.rightHandItem
                    copy.leftHandItem = anim.leftHandItem
                    copy.replyMode = anim.replyMode
                    copy.priority = anim.priority
                    copy.forcedPriority = anim.forcedPriority
                    copy.chatFrameIds = anim.chatFrameIds.copyOf()
                    copy.frameIDs = anim.frameIDs.copyOf()
                    copy.frameLengths = anim.frameLengths.copyOf()
                    copy.frameSounds = anim.frameSounds.copyOf()
                    copy.interleaveLeave = anim.interleaveLeave.copyOf()
                    copy.stretches = anim.stretches
                    copy.frameStep = anim.frameStep
                    copy.maxLoops = anim.maxLoops
                    anims.add(copy)
                    animModel.animations.add(copy)
                }
            },
            button("Remove Animation") {
                disableWhen(animModel.selected.isNull)
                action {
                    val confirm = alert(
                        Alert.AlertType.CONFIRMATION,
                        "Delete Animation",
                        "Are you sure you want to delete this animation?",
                        buttons = arrayOf(ButtonType.YES, ButtonType.NO)
                    )

                    val cache = animModel.cache.get()
                    if (cache != null && confirm.result === ButtonType.YES) {
                        val anim = animModel.selected.get()
                        anims.remove(anim)
                        animModel.animations.remove(anim)
                        cache.remove(2, 12, anim.id)
                        cache.index(2).update()
                    }
                }
            }
        )
        hbox {

            listview(animModel.animations) {
                animModel.selected.bind(selectionModel.selectedItemProperty())
                cellFormat {
                    text = "Animation ${it.id}"
                }
            }

            form {
                disableWhen(animModel.selected.isNull)
                fieldset("Configs") {
                    field("Forced Priority") {
                        spinner(property = animModel.forcedPriority, editable = true, min = 0, max = Int.MAX_VALUE) {
                            editor.stripNonNumeric()
                        }
                    }
                    field("Priority") {
                        spinner(property = animModel.priority, editable = true, min = 0, max = Int.MAX_VALUE) {
                            editor.stripNonNumeric()
                        }
                    }
                    field("Replay Mode") {
                        spinner(property = animModel.replayMode, editable = true, min = 0, max = Int.MAX_VALUE) {
                            editor.stripNonNumeric()
                        }
                    }
                }
                fieldset("Animation Items") {
                    spacing = 10.0
                    alignment = Pos.CENTER_LEFT
                    vbox {
                        spacing = 10.0
                        label("Left Hand Item")
                        label {
                            textProperty().bind(Bindings.createStringBinding({
                                val item = getItem(animModel.leftHandItem.get())
                                if (item != null && item.name != "null") {
                                    item.name
                                } else "Unknown"
                            }, animModel.leftHandItem))
                        }
                        button("Set Item").action {
                            setItem()
                        }
                    }
                    vbox {
                        spacing = 10.0
                        label("Right Hand Item")
                        label {
                            textProperty().bind(Bindings.createStringBinding({
                                val item = getItem(animModel.leftHandItem.get())
                                if (item != null && item.name != "null") {
                                    item.name
                                } else "Unknown"
                            }, animModel.leftHandItem))
                        }
                        button("Set Item").action {
                            setItem(false)
                        }
                    }
                }
            }
        }
    }

    private fun setItem(leftHand: Boolean = true) {
        val cache = animModel.cache.get()
        if (cache != null) {
            val scope = ItemSelectScope(cache)
            val sif = find<SelectItemFragment>(scope)
            sif.openModal(block = true)
            val result = sif.selectModel.selected.get()
            if (result != null) {
                if (leftHand) {
                    animModel.leftHandItem.set(result.id)
                } else {
                    animModel.rightHandItem.set(result.id)
                }
            }
        }
    }

    private fun loadAnimations() {
        val cache = animModel.cache.get()
        if (cache != null) {
            val animIds = cache.index(2).archive(12)?.fileIds() ?: intArrayOf()
            val list = mutableListOf<SequenceDefinition>()
            for (animId in animIds) {
                val data = cache.data(2, 12, animId)
                if (data != null) {
                    val def = anims.load(animId, data)
                    list.add(def)
                }
            }
            animModel.animations.setAll(list)
        }
    }

    private fun getItem(itemId: Int): ItemDefinition? {
        if (items[itemId] == null) {
            val cache = animModel.cache.get()
            if (cache != null) {
                val data = cache.data(2, 10, itemId)
                if (data != null) {
                    return items.load(itemId, data)
                }
            }
        } else {
            return items[itemId]!!
        }
        return null
    }

}