package com.javatar.plugin.definition.editor.ui.editor.cvars.varps

import com.javatar.plugin.definition.editor.ui.editor.cvars.model.VariableModel
import tornadofx.Fragment
import tornadofx.listview
import tornadofx.onChange

class VarpsFragment : Fragment("All Varps") {

    val varModel: VariableModel by inject()

    override val root = listview(varModel.varps) {
        selectionModel.selectedItemProperty().onChange {
            varModel.selectedVarp.set(it)
        }
        cellFormat {
            text = "Var Player ${it.definitionId}"
        }
    }

}