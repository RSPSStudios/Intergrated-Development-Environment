package com.javatar.api.ui.fs

import javafx.scene.control.ContextMenu
import org.pf4j.ExtensionPoint

interface ArchiveContextMenuExtension : ExtensionPoint {

    val archiveId: Int

    fun configureContextMenu(context: ContextMenu)

}