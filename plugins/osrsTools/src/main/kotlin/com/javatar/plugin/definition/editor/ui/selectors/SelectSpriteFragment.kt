package com.javatar.plugin.definition.editor.ui.selectors

import com.javatar.api.ui.utilities.datagrid
import com.javatar.osrs.definitions.impl.SpriteDefinition
import com.javatar.osrs.definitions.loaders.SpriteLoader
import com.javatar.osrs.tools.SpriteTools.toImage
import com.javatar.plugin.definition.editor.managers.ConfigDefinitionManager
import com.javatar.plugin.definition.editor.ui.selectors.models.SpriteSelectModel
import com.javatar.plugin.definition.editor.ui.selectors.scope.SpriteSelectScope
import javafx.geometry.Pos
import tornadofx.*

class SelectSpriteFragment : Fragment("Select Sprite") {

    override val scope = super.scope as SpriteSelectScope

    val selectModel: SpriteSelectModel by inject(scope)

    val sprites = ConfigDefinitionManager(SpriteLoader())

    init {
        loadSprites()
    }

    override val root = vbox {
        style = "-fx-base: #3f474f;"
        spacing = 10.0
        alignment = Pos.CENTER
        prefWidth = 790.0
        datagrid<SpriteDefinition> {
            itemsProperty.bind(selectModel.sprites)

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
                    imageview(it.toImage()) {
                        fitWidth = cellWidth
                        fitHeight = cellHeight
                    }
                    label("Sprite ${it.id}")
                }
            }
        }
        hbox {
            spacing = 15.0
            button("Use")
            button("Cancel")
        }
    }

    private fun loadSprites() {
        val cache = scope.cache
        val spriteIds = cache.index(8).archiveIds()
        val list = mutableListOf<SpriteDefinition>()
        for (spriteId in spriteIds) {
            val data = cache.data(8, spriteId)
            if (data != null) {
                val def = sprites.load(spriteId, data)
                if (def.sprites.size == 1) {
                    list.add(def.sprites[0])
                }
            }
        }
        selectModel.sprites.setAll(list)
    }

}