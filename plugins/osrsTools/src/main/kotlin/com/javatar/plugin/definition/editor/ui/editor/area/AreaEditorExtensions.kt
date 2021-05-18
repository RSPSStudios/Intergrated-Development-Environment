package com.javatar.plugin.definition.editor.ui.editor.area

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

@Extension
class AreaEditorExtensions : ArchiveContextMenuExtension, QuickToolExtension {
    override val indexId: Int = 2
    override val archiveId: Int = 35

    override fun configureContextMenu(context: ContextMenu, root: RootDirectory) {
        with(context) {
            item("Area Editor").action {
                find<AreaEditorFragment>().apply {
                    areaModel.cache.set(root.cache)
                }.openModal(block = true)
            }
        }
    }

    override fun applyQuickTool(menu: Menu, cachePath: String) {
        with(menu) {
            item("Area Editor (OSRS)").action {
                find<AreaEditorFragment>().apply {
                    areaModel.cache.set(CacheLibrary.create(cachePath))
                }.openModal(block = true)
            }
        }
    }
}