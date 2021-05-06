package com.javatar.plugin.definition.editor.ui.editor.items.wizard

import com.javatar.plugin.definition.editor.ui.editor.items.model.ItemCreationModel
import tornadofx.*

class ItemModelInfo : Fragment("Item Models") {

    val def: ItemCreationModel by inject()

    override val root = form {
        fieldset(title) {
            field("Inventory Model") {
                spinner(property = def.inventoryModel, editable = true, min = 0, max = Int.MAX_VALUE) {
                    this.editor.stripNonNumeric()
                }
            }
        }
    }

}