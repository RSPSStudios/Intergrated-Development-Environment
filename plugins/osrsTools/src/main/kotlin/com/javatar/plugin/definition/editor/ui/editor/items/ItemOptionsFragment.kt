package com.javatar.plugin.definition.editor.ui.editor.items

import com.javatar.plugin.definition.editor.ui.editor.items.model.ItemDefinitionModel
import com.javatar.plugin.definition.editor.ui.rsmenu.ActionEditor
import com.javatar.plugin.definition.editor.ui.rsmenu.ActionEditorFragment
import com.javatar.plugin.definition.editor.ui.rsmenu.model.ActionEditorModel
import javafx.beans.binding.Bindings
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import tornadofx.*

class ItemOptionsFragment : Fragment() {

    val def: ItemDefinitionModel by inject()
    val actionModel: ActionEditorModel by inject()

    override val root = form {

        fieldset("Ground Options") {
            field {
                add(ActionEditorFragment().apply {
                    actionModel.actions.bind(Bindings.createObjectBinding({
                        val list = FXCollections.observableArrayList(def.groundOptions)
                        list.add("Examine")
                        list.add("Cancel")
                        list
                    }, def.groundOptions))
                    actionModel.targetName.bind(def.name)
                    actionModel.maxOptions.set(def.groundOptions.size)
                    setOnSave {
                        val list = FXCollections.observableArrayList(actionModel.actions)
                        list.remove("Examine")
                        list.remove("Cancel")
                        def.groundOptions.setAll(list)
                    }
                })
            }
        }
        fieldset("Interface Options") {
            field {
                add(ActionEditorFragment().apply {
                    actionModel.actions.bind(Bindings.createObjectBinding({
                        val list = FXCollections.observableArrayList(def.interfaceOptions)
                        list.add("Examine")
                        list.add("Cancel")
                        list
                    }, def.interfaceOptions))
                    actionModel.targetName.bind(def.name)
                    actionModel.maxOptions.set(def.interfaceOptions.size)
                    setOnSave {
                        val list = FXCollections.observableArrayList(actionModel.actions)
                        list.remove("Examine")
                        list.remove("Cancel")
                        def.interfaceOptions.setAll(list)
                    }
                })
            }
        }

    }

}