package com.javatar.plugin.definition.editor.ui.rsmenu

import com.javatar.plugin.definition.editor.ui.rsmenu.model.ActionEditorModel
import javafx.scene.text.Font
import tornadofx.*

class ActionEditorFragment : Fragment() {

    val actionModel = ActionEditorModel()
    private var saveActions: () -> Unit = {}

    override val root = vbox {
        style {
            baseColor = c("#3f474f")
        }
        spacing = 5.0
        prefHeight = 500.0
        toolbar(
            button("Add Option") {
                disableWhen(actionModel.actions.sizeProperty().greaterThanOrEqualTo(actionModel.maxOptions))
                action {
                    actionModel.actions.add("New Option")
                }
            },
            button("Remove Option") {
                disableWhen(actionModel.selected.isNull.or(actionModel.selected.isEqualTo("null")))
                action {
                    val index = actionModel.actions.indexOf(actionModel.selected.get())
                    if(index != -1) {
                        actionModel.actions[index] = "null"
                    }
                }
            }
        )
        label("Options that are \"null\" will not show up.") {
            font = Font.font(16.0)
        }
        add(ActionEditor(actionModel))
        button("Save Actions"){
            action {
                saveActions()
            }
        }
    }

    fun setOnSave(save : () -> Unit) {
        saveActions = save
    }

}