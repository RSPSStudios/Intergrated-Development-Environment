package com.javatar.plugin.definition.editor.ui.editor.items

import com.javatar.plugin.definition.editor.ui.editor.items.model.ItemDefinitionModel
import tornadofx.*

class ItemOptionsFragment : Fragment() {

    val def: ItemDefinitionModel by inject()

    override val root = form {

        fieldset("Ground Options") {
            field {
                listview(def.groundOptions) {
                    prefWidth = 300.0
                    cellFormat {
                        prefWidthProperty().bind(this@listview.prefWidthProperty())
                        graphic = hbox {
                            spacing = 5.0
                            textfield(item)
                            button("Remove").action {
                                def.groundOptions[def.groundOptions.indexOf(item)] = "null"
                            }
                        }
                    }
                }
            }
        }
        fieldset("Interface Options") {
            field {
                listview(def.interfaceOptions) {
                    cellFormat {
                        prefWidthProperty().bind(this@listview.prefWidthProperty())
                        graphic = hbox {
                            spacing = 5.0
                            textfield(item)
                            button("Remove").action {
                                def.interfaceOptions[def.interfaceOptions.indexOf(item)] = "null"
                            }
                        }
                    }
                }
            }
        }

    }

}