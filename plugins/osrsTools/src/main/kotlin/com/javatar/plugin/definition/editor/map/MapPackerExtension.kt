package com.javatar.plugin.definition.editor.map

import com.javatar.api.ui.fs.IndexContextMenuExtension
import com.javatar.plugin.definition.editor.ui.MapPacker
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import org.pf4j.Extension
import tornadofx.action
import tornadofx.find

@Extension
class MapPackerExtension : IndexContextMenuExtension {

    override val indexId: Int = 5

    override fun configureContextMenu(context: ContextMenu) {
        context.items.add(MenuItem("Map Packer").apply {
            action {
                find<MapPacker>().openModal(block = true)
            }
        })
    }
}