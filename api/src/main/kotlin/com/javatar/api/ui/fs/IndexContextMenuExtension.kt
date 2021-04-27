package com.javatar.api.ui.fs

import com.javatar.api.fs.directories.RootDirectory
import javafx.scene.control.ContextMenu
import org.pf4j.ExtensionPoint

interface IndexContextMenuExtension : ExtensionPoint {

    val indexId: Int

    fun configureContextMenu(context: ContextMenu, root: RootDirectory)

}