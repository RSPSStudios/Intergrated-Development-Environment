package com.javatar.plugin.definition.editor.ui.selectors

import com.javatar.osrs.definitions.impl.VarpDefinition
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.ui.selectors.models.VarpSelectModel
import com.javatar.plugin.definition.editor.ui.selectors.scope.SelectVarpScope
import javafx.scene.input.MouseEvent
import tornadofx.Fragment
import tornadofx.listview
import tornadofx.vbox

class VarpSelectorFragment : Fragment("Select Variable Player") {

    override val scope = super.scope as SelectVarpScope

    val selectModel: VarpSelectModel by inject(scope)

    val varps = OldSchoolDefinitionManager.varps

    init {
        loadVarps()
    }

    override val root = vbox {
        style = "-fx-base: #3f474f;"
        listview(selectModel.varps) {
            selectModel.selected.bind(selectionModel.selectedItemProperty())
            cellFormat {
                text = "Variable Player ${it.definitionId}"
                addEventFilter(MouseEvent.MOUSE_CLICKED) { e ->
                    if (e.clickCount == 2) {
                        close()
                    }
                }
            }
        }
    }

    private fun loadVarps() {
        val cache = scope.cache
        val varpIds = cache.index(2).archive(16)?.fileIds() ?: intArrayOf()
        val list = mutableListOf<VarpDefinition>()
        for (varpId in varpIds) {
            val data = cache.data(2, 16, varpId)
            if (data != null) {
                list.add(varps.load(varpId, data))
            }
        }
        selectModel.varps.setAll(list)
    }

}