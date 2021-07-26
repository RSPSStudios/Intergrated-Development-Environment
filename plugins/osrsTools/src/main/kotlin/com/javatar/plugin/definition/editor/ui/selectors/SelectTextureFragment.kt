package com.javatar.plugin.definition.editor.ui.selectors

import com.javatar.api.ui.utilities.datagrid
import com.javatar.osrs.definitions.impl.SpriteDefinition
import com.javatar.osrs.definitions.impl.TextureDefinition
import com.javatar.osrs.definitions.loaders.SpriteLoader
import com.javatar.osrs.definitions.loaders.TextureLoader
import com.javatar.osrs.tools.SpriteTools.toImage
import com.javatar.plugin.definition.editor.managers.ConfigDefinitionManager
import com.javatar.plugin.definition.editor.ui.selectors.models.TextureSelectModel
import com.javatar.plugin.definition.editor.ui.selectors.scope.TextureSelectScope
import javafx.geometry.Pos
import tornadofx.*

class SelectTextureFragment : Fragment("Select Texture") {

    override val scope = super.scope as TextureSelectScope

    val selectModel: TextureSelectModel by inject(scope)

    val sprites = ConfigDefinitionManager(SpriteLoader())
    val textures = ConfigDefinitionManager(TextureLoader())

    init {
        loadTextures()
    }

    private fun loadTextures() {
        val cache = scope.cache

        val textureIds = cache.index(9).archive(0)?.fileIds() ?: intArrayOf()

        val list = mutableListOf<TextureDefinition>()
        for (textureId in textureIds) {
            val data = cache.data(9, 0, textureId)
            if (data != null) {
                val def = textures.loader.load(textureId, data)
                list.add(def)
            }
        }
        selectModel.textures.setAll(list)
    }

    override val root = vbox {
        style = "-fx-base: #3f474f;"
        spacing = 10.0
        alignment = Pos.CENTER
        prefWidth = 790.0

        datagrid<TextureDefinition> {
            itemsProperty.bind(selectModel.textures)

            singleSelect = true
            alignment = Pos.CENTER

            cellWidth = 237.0
            cellHeight = 225.0

            selectionTrigger("action") { it.clickCount == 2 }
            selectionTrigger { it.clickCount == 1 }

            onSelectionChange("action") {
                selectModel.selected.set(it)
                close()
            }
            onSelectionChange {
                selectModel.selected.set(it)
            }

            cellCache {
                vbox {
                    spacing = 10.0
                    alignment = Pos.CENTER
                    val sprite = getSprite(it.fileIds[0])
                    imageview(sprite.toImage())
                    label("Texture ${it.id}")
                }
            }

        }
        hbox {
            spacing = 5.0
            button("Use") {
                disableWhen(selectModel.selected.isNull)
            }.action {
                close()
            }
            button("Cancel").action {
                close()
            }
        }

    }

    private fun getSprite(spriteId: Int): SpriteDefinition {
        val cache = scope.cache
        val data = cache.data(8, spriteId)
        if (data != null) {
            return sprites.load(spriteId, data).sprites[0]
        }
        val sprite = SpriteDefinition()
        sprite.width = 1
        sprite.height = 1
        return sprite
    }

}