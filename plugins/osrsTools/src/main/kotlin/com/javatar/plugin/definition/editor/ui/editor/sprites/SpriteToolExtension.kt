package com.javatar.plugin.definition.editor.ui.editor.sprites

import com.displee.cache.CacheLibrary
import com.javatar.api.fs.directories.RootDirectory
import com.javatar.api.ui.fs.IndexContextMenuExtension
import com.javatar.api.ui.fs.QuickToolExtension
import javafx.scene.control.ContextMenu
import javafx.scene.control.Menu
import org.pf4j.Extension
import tornadofx.action
import tornadofx.find
import tornadofx.item
import tornadofx.separator

@Extension
class SpriteToolExtension : IndexContextMenuExtension, QuickToolExtension {
    override val indexId: Int = 8
    override fun configureContextMenu(context: ContextMenu, root: RootDirectory) {
        with(context) {
            separator()
            item("Sprite Editor").action {
                find<SpriteEditorFragment>().apply {
                    spriteModel.cache.set(root.cache)
                }.openModal(block = true)
            }
        }
    }

    override fun applyQuickTool(menu: Menu, cachePath: String) {
        with(menu) {
            item("Sprite Editor (OSRS)").action {
                find<SpriteEditorFragment>().apply {
                    spriteModel.cache.set(CacheLibrary.create(cachePath))
                }.openModal(block = true)
            }
        }
    }
}