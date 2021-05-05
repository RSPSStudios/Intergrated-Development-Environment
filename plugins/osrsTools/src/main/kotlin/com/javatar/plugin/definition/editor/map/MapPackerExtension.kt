package com.javatar.plugin.definition.editor.map

import com.displee.cache.CacheLibrary
import com.javatar.api.fs.directories.RootDirectory
import com.javatar.api.ui.fs.IndexContextMenuExtension
import com.javatar.api.ui.fs.QuickToolExtension
import com.javatar.plugin.definition.editor.ui.MapPacker
import javafx.scene.control.ContextMenu
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import org.pf4j.Extension
import tornadofx.action
import tornadofx.find
import tornadofx.item

@Extension
class MapPackerExtension : IndexContextMenuExtension, QuickToolExtension {

    override val indexId: Int = 5

    override fun configureContextMenu(context: ContextMenu, root: RootDirectory) {
        context.items.add(MenuItem("Map Packer").apply {
            action {
                find<MapPacker>().openModal(block = true)
            }
        })
    }

    override fun applyQuickTool(menu: Menu, cachePath: String) {
        with(menu) {
            item("Map Packer (OSRS)").action {
                find<MapPacker>().apply {
                    mapPackerModel.root.set(RootDirectory(CacheLibrary.create(cachePath)))
                }.openModal(block = true)
            }
        }
    }
}