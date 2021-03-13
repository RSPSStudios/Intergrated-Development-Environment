package com.javatar.api.ui.utilities

import javafx.event.EventTarget
import javafx.scene.Node
import javafx.scene.control.ContextMenu
import javafx.scene.control.Control

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 12 2021
 */

fun EventTarget.contextmenu(op: ContextMenu.() -> Unit = {}): ContextMenu {
    val menu = ContextMenu()
    op(menu)
    if (this is Control) {
        contextMenu = menu
    } else (this as? Node)?.apply {
        setOnContextMenuRequested { event ->
            menu.show(this, event.screenX, event.screenY)
            event.consume()
        }
    }
    return menu
}
