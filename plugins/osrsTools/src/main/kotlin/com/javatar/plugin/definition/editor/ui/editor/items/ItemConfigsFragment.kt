package com.javatar.plugin.definition.editor.ui.editor.items

import com.javatar.plugin.definition.editor.ui.editor.items.model.ItemDefinitionModel
import javafx.scene.control.SpinnerValueFactory
import tornadofx.*

class ItemConfigsFragment : Fragment() {

    val def: ItemDefinitionModel by inject()

    override val root = form {
        fieldset("Configurations") {
            field("Name") {
                textfield(def.name)
            }
            field("Cost") {
                spinner(property = def.cost) {
                    isEditable = true
                    valueFactory = NumberSpinnerFactory()
                }
            }
            field("Team") {
                spinner(property = def.team) {
                    isEditable = true
                    valueFactory = NumberSpinnerFactory()
                }
            }
            field("Shift Click Drop Index") {
                spinner(property = def.shiftClickDropIndex) {
                    isEditable = true
                    valueFactory = NumberSpinnerFactory()
                }
            }
        }
    }

    class NumberSpinnerFactory : SpinnerValueFactory<Number>() {
        override fun decrement(steps: Int) {
            this.value = (this.value.toInt() - steps)
        }

        override fun increment(steps: Int) {
            this.value = (this.value.toInt() + steps)
        }
    }

}