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
            }
            field("Varbit") {
                spinner(
                    editable = true,
                    property = model.varbitID,
                    valueFactory = NumberSpinnerValueFactory(0, Int.MAX_VALUE)
                ) {
                    editor.stripNonNumeric()
                }
            }
            field {
                button("Select Varp") {
                    action {
                        val scope = SelectVarpScope(model.cache.get())
                        val selector = find<VarpSelectorFragment>(scope)
                        selector.openModal(
                            block = true,
                            escapeClosesWindow = false
                        )
                        val resultVarp = selector.selectModel.selected.get()
                        if (resultVarp != null) {
                            model.varpID.set(resultVarp.definitionId)
                            val resultVarbit = selector.selectModel.selectedVarbit.get()
                            if (resultVarbit != null) {
                                model.varbitID.set(resultVarbit.definitionId)
                            } else {
                                model.varbitID.set(-1)
                            }
                        } else {
                            model.varpID.set(-1)
                        }
                    }
                }
            }
        }
        fieldset("Transformations") {
            disableWhen(model.varbitID.isEqualTo(-1).or(model.varpID.isEqualTo(-1)))
            listview(model.transformations) {
                cellFormat {
                    text = if(this.item != -1) {
                        val def = model.objects[this.item]
                        if(def != null) {
                            "${def.name ?: ""} - ${def.definitionId}"
                        } else {
                            "Object ${this.item}"
                        }
                    } else {
                        null
                    }
                }
            }
        }

    }

}