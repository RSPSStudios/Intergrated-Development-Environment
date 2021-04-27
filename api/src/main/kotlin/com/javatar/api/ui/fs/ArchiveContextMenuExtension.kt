package com.javatar.api.ui.fs

import com.javatar.api.fs.directories.RootDirectory
import javafx.scene.control.ContextMenu
import org.pf4j.ExtensionPoint

interface ArchiveContextMenuExtension : ExtensionPoint {

    val indexId: Int
    val archiveId: Int

    fun configureContextMenu(context: ContextMenu, root: RootDirectory)

}