package com.javatar.api.ui.fs

import com.javatar.api.fs.directories.RootDirectory
import javafx.scene.control.ContextMenu
import org.pf4j.ExtensionPoint

interface FileContextMenuExtension : ExtensionPoint {

    fun configureContextMenu(context: ContextMenu, root: RootDirectory)
}