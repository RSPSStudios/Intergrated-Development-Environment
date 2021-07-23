package com.javatar.plugin.definition.editor.ui.editor.world.objects.tabs

import com.javatar.plugin.definition.editor.ui.NumberSpinnerValueFactory
import com.javatar.plugin.definition.editor.ui.editor.world.objects.models.ObjectEditorModel
import com.javatar.plugin.definition.editor.ui.selectors.VarpSelectorFragment
import com.javatar.plugin.definition.editor.ui.selectors.scope.SelectVarpScope
import tornadofx.*

class ObjectVariablesFragment : Fragment("Object Variables") {

    val model: ObjectEditorModel by inject()

    override val root = form {

        fieldset {
            field("Variable Player") {
                spinner(
                    editable = true,
                    property = model.varpID,
                    valueFactory = NumberSpinnerValueFactory(0, Int.MAX_VALUE)
                ) {
                    editor.stripNonNumeric()
                }
                button("Select Varp") {
                    action {
                        val scope = SelectVarpScope(model.cache.get())
                        val selector = find<VarpSelectorFragment>(scope)
                        selector.openModal(
                            block = true,
                            escapeClosesWindow = false
                        )
                        val resultVarp = selector.selectModel.selected.get()
                        val resultVarbit = selector.selectModel.selectedVarbit.get()
                    }
                }
            }
        }

    }

}