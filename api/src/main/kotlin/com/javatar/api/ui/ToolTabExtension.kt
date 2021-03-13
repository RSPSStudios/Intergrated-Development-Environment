package com.javatar.api.ui

import javafx.scene.control.TabPane
import org.pf4j.ExtensionPoint

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

interface ToolTabExtension : ExtensionPoint {

    fun createToolTab(toolTabPane: TabPane)

}
