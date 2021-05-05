package com.javatar.plugin.definition.editor.ui.editor.items.wizard

import com.javatar.plugin.definition.editor.ui.editor.items.model.ItemCreationModel
import tornadofx.*

class SecondaryItemGenerationInfo : View("Secondary Item Generation") {

    val def: ItemCreationModel by inject()

    override val root = form {
        fieldset("Bank") {
            field {
                vbox {
                    spacing = 10.0
                    checkbox("Generate Bank Note", property = def.generateBankNote)
                    label("Generates a noted version of this item.") {
                        wrapWidthProperty.bind(this@field.widthProperty())
                    }
                }
            }
            field {
                vbox {
                    spacing = 10.0
                    checkbox("Generate Placeholder", property = def.generatePlaceholder)
                    label("Generates a Bank Placeholder version of this item.") {
                        wrapWidthProperty.bind(this@field.widthProperty())
                    }
                }
            }
        }
    }

}