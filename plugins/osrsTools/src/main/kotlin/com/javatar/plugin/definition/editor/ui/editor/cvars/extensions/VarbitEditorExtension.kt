package com.javatar.plugin.definition.editor.ui.editor.cvars.extensions

import com.javatar.api.fs.directories.RootDirectory
import com.javatar.api.ui.fs.ArchiveContextMenuExtension
import com.javatar.plugin.definition.editor.ui.editor.cvars.ClientVariableEditorFragment
import javafx.scene.control.ContextMenu
import org.pf4j.Extension
import tornadofx.action
import tornadofx.find
import tornadofx.item

@Extension
class VarbitEditorExtension : ArchiveContextMenuExtension {
    override val indexId: Int = 2
    override val archiveId: Int = 14

    override fun configureContextMenu(context: ContextMenu, root: RootDirectory) {
        with(context) {
            item("Varbit Editor").action {
                find<ClientVariableEditorFragment>().apply {
                    varModel.cache.set(root.cache)
                }.openModal(block = true)
            }
        }
    }
}