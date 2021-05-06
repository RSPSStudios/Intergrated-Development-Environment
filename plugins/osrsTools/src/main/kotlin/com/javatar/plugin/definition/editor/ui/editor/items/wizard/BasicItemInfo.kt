package com.javatar.plugin.definition.editor.ui.editor.items.wizard

import com.javatar.plugin.definition.editor.ui.editor.items.model.ItemCreationModel
import javafx.beans.binding.BooleanExpression
import tornadofx.*

class BasicItemInfo : View("Basic Information") {

    val def: ItemCreationModel by inject()

    override val complete: BooleanExpression = def.valid(def.name)

    override val root = form {
        fieldset(title) {
            field("Name") {
                textfield(def.name).required()
            }
            field("Cost") {
                spinner(editable = true, property = def.cost, min = 0, max = Int.MAX_VALUE) {
                    editor.stripNonNumeric()
                }
            }
            field("Stackable") {
                checkbox(property = def.stackable)
            }
            field("Tradeable") {
                checkbox(property = def.tradeable)
            }
            field("Members") {
                checkbox(property = def.members)
            }
        }
    }
}