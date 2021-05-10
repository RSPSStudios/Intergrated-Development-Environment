package com.javatar.plugin.definition.editor.ui.editor.cvars

import com.javatar.plugin.definition.editor.ui.editor.cvars.model.VariableModel
import tornadofx.Fragment
import tornadofx.onChange

class ClientVariableEditorFragment : Fragment("Client Variables Editor") {

    val varModel: VariableModel by inject()

    init {
        varModel.cache.onChange {
            if(it != null) {
                varModel.loadVariables()
            }
        }
    }

    override val root = VariableEditorFragment().root

}