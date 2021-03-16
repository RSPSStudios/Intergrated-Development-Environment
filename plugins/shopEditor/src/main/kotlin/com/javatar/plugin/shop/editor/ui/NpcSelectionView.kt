package com.javatar.plugin.shop.editor.ui

import com.javatar.api.ui.utilities.datagrid
import com.javatar.plugin.shop.editor.ui.models.NpcSelectionModel
import com.javatar.plugin.shop.editor.ui.models.ShopCacheConfigModel
import javafx.beans.binding.Bindings
import tornadofx.*

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 15 2021
 */

class NpcSelectionView : Fragment() {

    val shopCacheConfigModel: ShopCacheConfigModel by inject()
    val npcSelectModel: NpcSelectionModel by inject()

    init {
        npcSelectModel.searchText.onChange { text ->
            val npcs = npcSelectModel.npcs
            val provider = shopCacheConfigModel.npcProvider.get()
            if (text.isNullOrEmpty()) {
                npcSelectModel.filteredNpcs.setAll(npcSelectModel.npcs)
            } else if (provider != null && text.isInt() && text.toInt() in provider.npcIds()) {
                npcSelectModel.filteredNpcs.setAll(text.toInt())
            } else {
                val filtered = npcs.filter { provider.getDefinition(it).name.contains(text) }
                npcSelectModel.filteredNpcs.setAll(filtered)
            }
        }
        npcSelectModel.filteredNpcs.setAll(npcSelectModel.npcs)
    }

    override val root = vbox {
        prefWidth = 1000.0
        prefHeight = 500.0
        style = "-fx-base: #3f474f;"
        hbox {
            spacing = 10.0
            button("Clear") {
                textfield(npcSelectModel.searchText) {
                    promptText = "Search Npc"
                }
            }.action {
                npcSelectModel.searchText.set("")
            }
        }
        datagrid<Int> {

            prefWidth = 1000.0
            prefWidth = 500.0

            cellWidth = 300.0
            cellHeight = 200.0

            itemsProperty.bind(npcSelectModel.filteredNpcs)

            selectionTrigger("action") { it.clickCount == 2 }
            selectionTrigger { it.clickCount == 1 }

            onSelectionChange("action") {
                npcSelectModel.result.set(it)
                this@NpcSelectionView.close()
            }

            cellCache {
                label {
                    textProperty().bind(Bindings.createStringBinding({
                        if (shopCacheConfigModel.npcProvider.get() != null) {
                            shopCacheConfigModel.npcProvider.get().getDefinition(it).name
                        } else {
                            "Npc $it"
                        }
                    }, shopCacheConfigModel.npcProvider))
                }
            }
        }
    }

}
