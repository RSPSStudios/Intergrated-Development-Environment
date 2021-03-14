package com.javatar.api.ui

import javafx.scene.layout.VBox
import org.pf4j.ExtensionPoint

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

interface PluginPanelExtension : ExtensionPoint {

    fun createPluginInformationPanel(infoPanel: VBox)

}
