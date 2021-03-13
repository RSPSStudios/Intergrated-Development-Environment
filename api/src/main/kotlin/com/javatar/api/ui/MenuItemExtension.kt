package com.javatar.api.ui

import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import org.pf4j.ExtensionPoint

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

interface MenuItemExtension : ExtensionPoint {

    fun createMenuItem(pluginsMenu: Menu, menuBar: MenuBar)

}
