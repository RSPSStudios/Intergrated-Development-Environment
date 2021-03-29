package com.javatar.api.ui.fs

import javafx.scene.control.ContextMenu
import org.pf4j.ExtensionPoint

interface FileContextMenuExtension : ExtensionPoint {

    fun configureContextMenu(context: ContextMenu)

}