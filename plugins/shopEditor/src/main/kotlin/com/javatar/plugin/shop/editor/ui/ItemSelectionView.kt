package com.javatar.plugin.shop.editor.ui

import com.javatar.api.ui.utilities.datagrid
import com.javatar.plugin.shop.editor.ui.models.ItemSelectionModel
import com.javatar.plugin.shop.editor.ui.models.ShopCacheConfigModel
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.binding.Bindings
import javafx.embed.swing.SwingFXUtils
import javafx.geometry.Pos
import javafx.scene.image.ImageView
import tornadofx.*

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 15 2021
 */

class ItemSelectionView : Fragment() {

    val cacheModel: ShopCacheConfigModel by inject()
    val itemSelectModel: ItemSelectionModel by inject()

    init {
        itemSelectModel.searchText.onChange { text ->
            val npcs = itemSelectModel.items
            val provider = cacheModel.itemProvider.get()
            if (text.isNullOrEmpty()) {
                itemSelectModel.filteredItems.setAll(itemSelectModel.items)
            } else if (provider != null && text.isInt() && text.toInt() in provider.itemIds()) {
                itemSelectModel.filteredItems.setAll(text.toInt())
            } else {
                val filtered = npcs.filter { provider.getDefinition(it).name.contains(text) }
                itemSelectModel.filteredItems.setAll(filtered)
            }
        }
        itemSelectModel.filteredItems.setAll(itemSelectModel.items)
    }

    override val root = vbox {
        prefWidth = 1000.0
        prefHeight = 500.0
        style = "-fx-base: #3f474f;"
        hbox {
            spacing = 10.0
            button("Clear") {
                textfield(itemSelectModel.searchText) {
                    promptText = "Search Npc"
                }
            }.action {
                itemSelectModel.searchText.set("")
            }
        }
        datagrid<Int>(itemSelectModel.filteredItems) {

            prefWidth = 1000.0
            prefWidth = 500.0

            cellWidth = 300.0
            cellHeight = 200.0

            selectionTrigger { it.clickCount == 1 }
            selectionTrigger("action") { it.clickCount == 2 }

            onSelectionChange("action") {
                itemSelectModel.result.set(it)
                close()
            }

            cellCache { value ->
                vbox {
                    alignment = Pos.CENTER
                    spacing = 10.0
                    dynamicContent(cacheModel.itemProvider) {
                        if (it != null) {
                            val view = ImageView(
                                SwingFXUtils.toFXImage(
                                    cacheModel.spriteFactory.get()
                                        .createSprite(
                                            cacheModel.itemProvider.get(),
                                            cacheModel.modelProvider.get(),
                                            cacheModel.spriteProvider.get(),
                                            cacheModel.textureProvider.get(),
                                            value,
                                            1,
                                            1,
                                            3153952,
                                            false
                                        ),
                                    null
                                )
                            )
                            add(view)
                        } else add(FontAwesomeIconView(FontAwesomeIcon.FILE).also { icon -> icon.glyphSize = 64 })
                    }
                    label {
                        textProperty().bind(Bindings.createStringBinding({
                            if (cacheModel.itemProvider.get() != null) {
                                cacheModel.itemProvider.get().getDefinition(value).name
                            } else {
                                "Item $value"
                            }
                        }, cacheModel.itemProvider))
                    }
                }
            }

        }
    }

}
