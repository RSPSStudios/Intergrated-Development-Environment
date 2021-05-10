package com.javatar.plugin.definition.editor.ui.editor.cvars.varps

import com.javatar.plugin.definition.editor.ui.editor.cvars.model.VariableModel
import tornadofx.Fragment
import tornadofx.listview
import tornadofx.onChange

class UnusedVarpsFragment : Fragment("Unused Varps") {

    val varModel: VariableModel by inject()

    override val root = listview(varModel.unusedVarps) {
        selectionModel.selectedItemProperty().onChange {
            varModel.selectedVarp.set(it)
        }
        cellFormat {
            text = "Unused Varp ${it.definitionId}"
        }
    }

}