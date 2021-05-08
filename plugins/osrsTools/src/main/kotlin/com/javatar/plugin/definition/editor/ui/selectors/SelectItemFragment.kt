package com.javatar.plugin.definition.editor.ui.selectors

import com.javatar.osrs.definitions.impl.ItemDefinition
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.ui.selectors.models.ItemSelectModel
import com.javatar.plugin.definition.editor.ui.selectors.scope.ItemSelectScope
import javafx.beans.binding.Bindings
import javafx.scene.input.MouseEvent
import tornadofx.*

class SelectItemFragment : Fragment("Select Item") {

    override val scope = super.scope as ItemSelectScope

    val selectModel: ItemSelectModel by inject(scope)

    val items = OldSchoolDefinitionManager.items

    init {
        loadItems()
        selectModel.searchText.onChange {
            if(it != null && it.isEmpty()) {
                setFilteredItems()
            }
        }
    }

    override val root = vbox {
        style = "-fx-base: #3f474f;"
        hbox {
            textfield(selectModel.searchText).action {
                setFilteredItems()
            }
            button(Bindings.createStringBinding({
                if(selectModel.searchText.get().isEmpty()) "Search" else "Clear"
            }, selectModel.searchText)).action {
                setFilteredItems()
            }
        }

        listview(selectModel.items) {
            selectModel.selected.bind(selectionModel.selectedItemProperty())
            cellFormat {
                text = if(it.name == null || it.name.isEmpty()) {
                    "Item ${it.id}"
                } else "${it.name} - ${it.id}"
                addEventFilter(MouseEvent.MOUSE_CLICKED) { e ->
                    if(e.clickCount == 2) {
                        close()
                    }
                }
            }
        }

    }

    private fun loadItems() {
        val cache = scope.cache
        val itemIds = cache.index(2).archive(10)?.fileIds() ?: intArrayOf()
        val list = mutableListOf<ItemDefinition>()
        for (itemId in itemIds) {
            val data = cache.data(2, 10, itemId)
            if(data != null) {
                list.add(items.load(itemId, data))
            }
        }
        selectModel.items.setAll(list)
    }

    private fun setFilteredItems() {
        val text = selectModel.searchText.get()
        if(text.isNotEmpty() && text.isNotBlank()) {
            if(text.isInt()) {
                val def = items.definitions.values.find { it.id == text.toInt() }
                selectModel.items.setAll(def)
            } else {
                val filtered = items.definitions.values.filter { text.lowercase() in it.name.lowercase() }
                selectModel.items.setAll(filtered)
            }
        } else {
            selectModel.items.setAll(items.definitions.values.toList())
        }
    }

}