package com.javatar.plugin.definition.editor.ui.editor.overlay

import com.javatar.api.http.Client
import com.javatar.api.http.StringBody
import com.javatar.api.ui.models.AccountModel
import com.javatar.osrs.definitions.impl.OverlayDefinition
import com.javatar.osrs.definitions.impl.SpriteDefinition
import com.javatar.osrs.definitions.impl.TextureDefinition
import com.javatar.osrs.definitions.loaders.OverlayLoader
import com.javatar.osrs.tools.SpriteTools.toImage
import com.javatar.plugin.definition.editor.OsrsDefinitionEditor.Companion.gson
import com.javatar.plugin.definition.editor.managers.ConfigDefinitionManager
import com.javatar.plugin.definition.editor.managers.SpriteProvider
import com.javatar.plugin.definition.editor.managers.TextureProvider
import com.javatar.plugin.definition.editor.ui.editor.overlay.model.OverlayEditorModel
import com.javatar.plugin.definition.editor.ui.selectors.SelectTextureFragment
import com.javatar.plugin.definition.editor.ui.selectors.scope.TextureSelectScope
import javafx.scene.control.Alert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.javafx.JavaFx
import tornadofx.*

class OverlayEditorFragment : Fragment("Overlay Editor") {

    val overlayModel: OverlayEditorModel by inject()

    val overlays = ConfigDefinitionManager(OverlayLoader())

    val account: AccountModel by di()
    val client: Client by di()

    init {

        overlayModel.cache.onChange {

            if (it != null) {
                overlayModel.textureProvider.set(TextureProvider(it))
                overlayModel.spriteProvider.set(SpriteProvider(it))
                loadOverlays()
            }

        }

    }

    override val root = vbox {
        style = "-fx-base: #3f474f;"
        toolbar(
            button("Pack Overlay") {
                disableWhen(overlayModel.overlay.isNull)
                action {
                    val overlay = overlayModel.commitOverlay()
                    val json = gson.toJson(overlay)
                    client.post<ByteArray>("tools/osrs/overlays", StringBody(json), account.activeCredentials.get())
                        .catch {
                            alert(Alert.AlertType.ERROR, "Packing Error", it.message)
                            emit(byteArrayOf())
                        }
                        .onEach {
                            if (it.isNotEmpty()) {
                                val cache = overlayModel.cache.get()
                                if (cache != null) {
                                    cache.put(2, 4, overlay.id, it)
                                    cache.index(2).update()
                                    alert(
                                        Alert.AlertType.INFORMATION,
                                        "Overlay Packing",
                                        "Successfully packed overlay."
                                    )
                                }
                            }
                        }
                        .launchIn(CoroutineScope(Dispatchers.JavaFx))
                }
            },
            button("Add Overlay") {
                action {
                    val overlay = OverlayDefinition()
                    overlay.id = overlays.nextId
                    overlays.add(overlay)
                    overlayModel.overlays.add(overlay)
                }
            },
            button("Remove Overlay") {
                disableWhen(overlayModel.overlay.isNull)
                action {
                    val cache = overlayModel.cache.get()
                    val overlay = overlayModel.overlay.get()
                    if (cache != null && overlay != null) {
                        overlays.remove(overlay)
                        overlayModel.overlays.remove(overlay)
                        cache.remove(2, 4, overlay.id)
                        cache.index(2).update()
                    }
                }
            }
        )
        hbox {
            spacing = 10.0
            listview(overlayModel.overlays) {
                overlayModel.overlay.bind(selectionModel.selectedItemProperty())
                cellFormat {
                    text = "Overlay ${it.id}"
                }
            }
            vbox {
                prefWidth = 300.0
                prefHeight = this@hbox.prefHeight
                dynamicContent(overlayModel.overlay) {
                    if (it != null) {
                        form {
                            fieldset("Texture") {
                                spacing = 10.0
                                vbox {
                                    dynamicContent(overlayModel.textureId) { num ->
                                        if (num != null) {
                                            val id = num.toInt()
                                            if (id != -1) {
                                                val tdef = getTexture(id)
                                                if (tdef.fileIds.size == 1) {
                                                    val sprite = getSprite(tdef.fileIds[0])
                                                    imageview(sprite.toImage())
                                                }
                                            }
                                        }
                                    }
                                }
                                button("Set Texture").action {
                                    val cache = overlayModel.cache.get()
                                    if (cache != null) {
                                        val scope = TextureSelectScope(cache)
                                        val stf = find<SelectTextureFragment>(scope)
                                        stf.openModal(block = true)
                                        val result = stf.selectModel.selected.get()
                                        if (result != null) {
                                            overlayModel.textureProvider.get()
                                                .textures.add(result)
                                            overlayModel.textureId.set(result.id)
                                        }
                                    }
                                }
                            }
                            fieldset("Configs") {
                                field("Hides Underlay") {
                                    checkbox(property = overlayModel.hidesUnderlay)
                                }
                                field("Color") {
                                    colorpicker(overlayModel.color)
                                }
                                field("Secondary Color") {
                                    colorpicker(overlayModel.secondColor)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getTexture(textureId: Int): TextureDefinition {
        val textureProvider = overlayModel.textureProvider.get()
        if (textureProvider != null) {
            return textureProvider.getDefinition(textureId)
        }
        return TextureDefinition()
    }

    private fun getSprite(spriteId: Int): SpriteDefinition {
        val spriteProvider = overlayModel.spriteProvider.get()
        if (spriteProvider != null) {
            return spriteProvider.getDefinition(spriteId).sprites[0]
        }
        return SpriteDefinition()
    }

    private fun loadOverlays() {
        val cache = overlayModel.cache.get()
        if (cache != null) {
            val overlayIds = cache.index(2).archive(4)?.fileIds() ?: intArrayOf()
            val overlayList = mutableListOf<OverlayDefinition>()
            for (overlayId in overlayIds) {
                val data = cache.data(2, 4, overlayId)
                if (data != null) {
                    val def = overlays.load(overlayId, data)
                    overlayList.add(def)
                }
            }
            overlayModel.overlays.setAll(overlayList)
        }
    }
}