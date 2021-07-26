package com.javatar.plugin.definition.editor.ui.editor.textures

import com.javatar.api.http.Client
import com.javatar.api.http.StringBody
import com.javatar.api.ui.models.AccountModel
import com.javatar.osrs.definitions.impl.SpriteDefinition
import com.javatar.osrs.definitions.impl.TextureDefinition
import com.javatar.osrs.definitions.loaders.SpriteLoader
import com.javatar.osrs.definitions.loaders.TextureLoader
import com.javatar.osrs.tools.SpriteTools.toImage
import com.javatar.plugin.definition.editor.OsrsDefinitionEditor.Companion.gson
import com.javatar.plugin.definition.editor.managers.ConfigDefinitionManager
import com.javatar.plugin.definition.editor.ui.editor.textures.models.TextureEditorModel
import com.javatar.plugin.definition.editor.ui.selectors.SelectSpriteFragment
import com.javatar.plugin.definition.editor.ui.selectors.scope.SpriteSelectScope
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

class TextureEditorFragment : Fragment("Texture Editor") {

    val textureModel: TextureEditorModel by inject()

    val textures = ConfigDefinitionManager(TextureLoader())

    val accountModel: AccountModel by di()
    val client: Client by di()

    init {
        textureModel.cache.onChange {
            if (it != null) {
                loadTextures()
            }
        }
    }

    override val root = vbox {
        style = "-fx-base: #3f474f;"
        toolbar(
            button("Pack Texture") {
                disableWhen(textureModel.selected.isNull)
                action {
                    val creds = accountModel.activeCredentials.get()
                    val cache = textureModel.cache.get()
                    if (creds != null && cache != null) {
                        textureModel.commitTexture()
                        val tex = textureModel.selected.get()
                        val json = gson.toJson(tex)
                        client.post<ByteArray>("tools/osrs/textures", StringBody(json), creds)
                            .catch {
                                alert(Alert.AlertType.ERROR, "Error packing texture", it.message)
                                emit(byteArrayOf())
                            }
                            .onEach {
                                if (it.isNotEmpty()) {
                                    cache.put(9, 0, it)
                                    cache.index(9).update()
                                    alert(
                                        Alert.AlertType.INFORMATION,
                                        "Packing Texture",
                                        "Successfully packed texture."
                                    )
                                }
                            }.launchIn(CoroutineScope(Dispatchers.JavaFx))
                    }
                }
            },
            button("Add Texture") {
                action {
                    val newTex = TextureDefinition()
                    newTex.id = textures.nextId
                    textures.add(newTex)
                    textureModel.textures.add(newTex)
                }
            },
            button("Remove Texture") {
                disableWhen(textureModel.selected.isNull)
                action {
                    val cache = textureModel.cache.get()
                    val tex = textureModel.selected.get()
                    if (cache != null) {
                        val remove = alert(
                            Alert.AlertType.CONFIRMATION,
                            "Delete Texture ${tex.id}",
                            "Are you sure you want to delete texture ${tex.id}?",
                            buttons = arrayOf(ButtonType.YES, ButtonType.NO)
                        )
                        val result = remove.result
                        if (result === ButtonType.YES) {
                            cache.remove(9, 0, tex.id)
                            cache.index(9).update()
                        }
                    }
                }
            }
        )
        hbox {
            prefWidth = 770.0
            listview(textureModel.textures) {
                textureModel.selected.bind(selectionModel.selectedItemProperty())
                cellFormat {
                    text = "Texture ${it.id}"
                }
            }
            accordion(buildFileIdsPane(), buildConfigsPane()) {
                disableWhen(textureModel.selected.isNull)
            }
        }
    }

    private fun buildConfigsPane() = titledpane("Configs") {
        form {
            fieldset("Animation Configs") {
                field("Direction") {
                    spinner(property = textureModel.animationDirection, editable = true, min = 1, max = 4) {
                        editor.stripNonNumeric()
                    }
                }
                field("Speed") {
                    spinner(property = textureModel.animationSpeed, editable = true, min = 0, max = 255) {
                        editor.stripNonNumeric()
                    }
                }
            }
        }
    }

    private fun buildFileIdsPane() = titledpane("Sprites") {
        vbox {
            hbox {
                button("Add Sprite").action {
                    val cache = textureModel.cache.get()
                    if (cache != null) {
                        val scope = SpriteSelectScope(cache)
                        val dialog = find<SelectSpriteFragment>(scope)
                        dialog.openModal(block = true)
                        if (dialog.selectModel.selected.get() != null) {
                            val sprite = dialog.selectModel.selected.get()
                            textureModel.spriteIds.add(sprite.id)
                        }
                    }
                }
            }
            datagrid<Int> {
                itemsProperty.bind(textureModel.spriteIds)

                cellWidth = 237.0
                cellHeight = 225.0

                cellFormat {
                    graphic = vbox {
                        spacing = 10.0
                        alignment = Pos.CENTER
                        val sprite = getSprite(it)
                        imageview(sprite.toImage())
                        button("Set Sprite").action {
                            val cache = textureModel.cache.get()
                            if (cache != null) {
                                val scope = SpriteSelectScope(cache)
                                val dialog = find<SelectSpriteFragment>(scope)
                                dialog.openModal(block = true)
                                if (dialog.selectModel.selected.get() != null) {
                                    val sprite = dialog.selectModel.selected.get()
                                    val index = textureModel.spriteIds.indexOf(it)
                                    textureModel.spriteIds[index] = sprite.id
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getSprite(spriteId: Int): SpriteDefinition {
        val group = ConfigDefinitionManager(SpriteLoader())[spriteId]
        if (group == null) {
            val cache = textureModel.cache.get()
            if (cache != null) {
                val data = cache.data(8, spriteId)
                if (data != null) {
                    return ConfigDefinitionManager(SpriteLoader()).load(spriteId, data).sprites[0]
                }
            }
        } else {
            return ConfigDefinitionManager(SpriteLoader())[spriteId]?.sprites?.get(0) ?: SpriteDefinition()
        }
        return SpriteDefinition()
    }

    private fun loadTextures() {
        val cache = textureModel.cache.get()
        if (cache != null) {
            val textureIds = cache.index(9).archive(0)?.fileIds() ?: intArrayOf()
            val list = mutableListOf<TextureDefinition>()
            for (textureId in textureIds) {
                val data = cache.data(9, 0, textureId)
                if (data != null) {
                    val def = textures.load(textureId, data)
                    list.add(def)
                }
            }
            textureModel.textures.setAll(list)
        }
    }

}