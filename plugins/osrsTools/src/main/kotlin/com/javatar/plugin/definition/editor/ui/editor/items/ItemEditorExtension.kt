package com.javatar.plugin.definition.editor.ui.editor.items

import com.javatar.api.fs.directories.RootDirectory
import com.javatar.api.ui.fs.ArchiveContextMenuExtension
import javafx.scene.control.ContextMenu
import org.pf4j.Extension
import tornadofx.action
import tornadofx.find
import tornadofx.item
import tornadofx.separator

@Extension
class ItemEditorExtension : ArchiveContextMenuExtension {
    override val indexId: Int = 2
    override val archiveId: Int = 10

    override fun configureContextMenu(context: ContextMenu, root: RootDirectory) {
        with(context) {
            separator()
            item("Item Editor").action {
                find<ItemEditorView>().apply {
                    cacheProperty.set(root.cache)
                }.openModal(block = true)
            }
        }
    }
}