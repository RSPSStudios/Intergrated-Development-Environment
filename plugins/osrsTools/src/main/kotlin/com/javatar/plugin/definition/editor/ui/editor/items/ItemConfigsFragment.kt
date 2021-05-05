package com.javatar.plugin.definition.editor.ui.editor.items

import com.javatar.plugin.definition.editor.ui.editor.items.model.ItemDefinitionModel
import tornadofx.*

class ItemConfigsFragment : Fragment() {

    val def: ItemDefinitionModel by inject()

    override val root = form {
        fieldset("Configurations") {
            field("Name") {
                textfield(def.name)
            }
            field("Cost") {
                spinner(property = def.cost, min = 0, max = Integer.MAX_VALUE) {
                    isEditable = true
                    editor.stripNonNumeric()
                }
            }
            field("Team") {
                spinner(property = def.team, min = 0, max = Integer.MAX_VALUE) {
                    isEditable = true
                    editor.stripNonNumeric()
                }
            }
            field("Shift Click Drop Index") {
                spinner(property = def.shiftClickDropIndex, min = -2, max = Integer.MAX_VALUE) {
                    isEditable = true
                    editor.stripNonNumeric()
                }
            }
            field("Tradeable") {
                checkbox(property = def.isTradeable)
            }
            field("Members") {
                checkbox(property = def.isMembers)
            }
        }
    }
}