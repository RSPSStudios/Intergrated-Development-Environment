package com.javatar.plugin.definition.editor.ui.editor.textures

import com.displee.cache.CacheLibrary
import com.javatar.api.fs.directories.RootDirectory
import com.javatar.api.fs.extensions.ArchiveTypeExtension
import com.javatar.api.ui.fs.ArchiveContextMenuExtension
import com.javatar.api.ui.fs.QuickToolExtension
import javafx.scene.control.ContextMenu
import javafx.scene.control.Menu
import org.pf4j.Extension
import tornadofx.action
import tornadofx.find
import tornadofx.item

@Extension
class TextureEditorExtension : ArchiveContextMenuExtension, QuickToolExtension {
    override val indexId: Int = 9
    override val archiveId: Int = 0

    override fun configureContextMenu(context: ContextMenu, root: RootDirectory) {
        with(context) {
            item("Texture Editor").action {
                find<TextureEditorFragment>().apply {
                    textureModel.cache.set(root.cache)
                }.openModal(block = true)
            }
        }
    }
    override fun applyQuickTool(menu: Menu, cachePath: String) {
        with(menu) {
            item("Texture Editor (OSRS)").action {
                find<TextureEditorFragment>().apply {
                    textureModel.cache.set(CacheLibrary.create(cachePath))
                }.openModal(block = true)
            }
        }
    }
}