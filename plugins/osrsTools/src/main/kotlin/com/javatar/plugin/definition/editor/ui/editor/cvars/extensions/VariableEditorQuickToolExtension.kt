package com.javatar.plugin.definition.editor.ui.editor.cvars.extensions

import com.displee.cache.CacheLibrary
import com.javatar.api.ui.fs.QuickToolExtension
import com.javatar.plugin.definition.editor.ui.editor.cvars.ClientVariableEditorFragment
import javafx.scene.control.Menu
import org.pf4j.Extension
import tornadofx.action
import tornadofx.find
import tornadofx.item

@Extension
class VariableEditorQuickToolExtension : QuickToolExtension {
    override fun applyQuickTool(menu: Menu, cachePath: String) {
        with(menu) {
            item("Variable Editor (OSRS)").action {
                find<ClientVariableEditorFragment>().apply {
                    varModel.cache.set(CacheLibrary.create(cachePath))
                }.openModal(block = true)
            }
        }
    }
}