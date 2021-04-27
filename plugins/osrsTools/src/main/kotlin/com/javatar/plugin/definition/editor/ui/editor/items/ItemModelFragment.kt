package com.javatar.plugin.definition.editor.ui.editor.items

import com.javatar.plugin.definition.editor.ui.editor.items.model.ItemDefinitionModel
import tornadofx.*

class ItemModelFragment : Fragment() {

    val def: ItemDefinitionModel by inject()

    override val root = form {
        fieldset("Male Models") {
            field("Male Model 1") {
                spinner(property = def.maleModel0, editable = true)
            }
            field("Male Model 2") {
                spinner(property = def.maleModel1, editable = true)
            }
            field("Male Model 3") {
                spinner(property = def.maleModel2, editable = true)
            }
            field("Male Head Model 1") {
                spinner(property = def.maleHeadModel0, editable = true)
            }
            field("Male Head Model 2") {
                spinner(property = def.maleHeadModel1, editable = true)
            }
        }
        fieldset("Female Models") {
            field("Female Model 1") {
                spinner(property = def.femaleModel0, editable = true)
            }
            field("Female Model 2") {
                spinner(property = def.femaleModel1, editable = true)
            }
            field("Female Model 3") {
                spinner(property = def.femaleModel2, editable = true)
            }
            field("Female Head Model 1") {
                spinner(property = def.femaleHeadModel0, editable = true)
            }
            field("Female Head Model 2") {
                spinner(property = def.femaleHeadModel1, editable = true)
            }
        }
        fieldset("Model Offsets") {
            field("Male Offset") {
                spinner(property = def.maleOffset, editable = true)
            }
            field("Female Offset") {
                spinner(property = def.femaleOffset, editable = true)
            }
        }
    }
}