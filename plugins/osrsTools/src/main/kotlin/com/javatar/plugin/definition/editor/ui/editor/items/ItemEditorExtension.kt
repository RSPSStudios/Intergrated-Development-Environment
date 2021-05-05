package com.javatar.plugin.definition.editor.ui.editor.items

import com.displee.cache.CacheLibrary
import com.javatar.api.fs.directories.RootDirectory
import com.javatar.api.ui.fs.ArchiveContextMenuExtension
import com.javatar.api.ui.fs.QuickToolExtension
import javafx.scene.control.ContextMenu
import javafx.scene.control.Menu
import org.pf4j.Extension
import tornadofx.action
import tornadofx.find
import tornadofx.item
import tornadofx.separator

@Extension
class ItemEditorExtension : ArchiveContextMenuExtension, QuickToolExtension {
    override val indexId: Int = 2
    override val archiveId: Int = 10

    override fun configureContextMenu(context: ContextMenu, root: RootDirectory) {
        with(context) {
            separator()
            item("Item Editor").action {
                find<ItemEditorView>().apply {
                    definitionModel.cacheProperty.set(root.cache)
                }.openModal(block = true)
            }
        }
    }

    override fun applyQuickTool(menu: Menu, cachePath: String) {
        with(menu) {
            item("Item Editor (OSRS)").action {
                find<ItemEditorView>().apply {
                    definitionModel.cacheProperty.set(CacheLibrary.create(cachePath))
                }.openModal(block = true)
            }
        }
    }
}