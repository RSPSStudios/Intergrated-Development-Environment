package com.javatar.plugin.definition.editor.ui.editor.world.objects.tabs

import com.javatar.plugin.definition.editor.ui.editor.world.objects.models.ObjectEditorModel
import com.javatar.plugin.definition.editor.ui.rsmenu.ActionEditorFragment
import javafx.beans.binding.Bindings
import javafx.collections.FXCollections
import tornadofx.Fragment
import tornadofx.vbox

class ObjectActionsFragment : Fragment("Object Actions") {

    val model: ObjectEditorModel by inject()

    override val root = vbox {
        add<ActionEditorFragment> {
            actionModel.actions.bind(Bindings.createObjectBinding({
                val list = FXCollections.observableArrayList(model.actions)
                list.add("Examine")
                list.add("Cancel")
                list.replaceAll {
                    it ?: "null"
                }
                list
            }, model.actions))
            actionModel.targetName.bind(model.name)
            actionModel.maxOptions.set(model.actions.size)
            setOnSave {
                val list = FXCollections.observableArrayList(actionModel.actions)
                list.remove("Examine")
                list.remove("Cancel")
                list.replaceAll { if(it == "null") null else it }
                model.actions.setAll(list)
            }
        }
    }
}