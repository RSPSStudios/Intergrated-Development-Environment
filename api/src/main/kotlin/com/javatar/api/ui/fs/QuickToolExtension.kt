package com.javatar.api.ui.fs

import javafx.scene.control.Menu
import org.pf4j.ExtensionPoint

interface QuickToolExtension : ExtensionPoint {

    fun applyQuickTool(menu: Menu, cachePath: String)

}