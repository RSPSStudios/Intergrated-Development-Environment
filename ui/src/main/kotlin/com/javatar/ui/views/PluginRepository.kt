package com.javatar.ui.views

import com.javatar.api.ui.PluginPanelExtension
import com.javatar.ui.data.PluginInformation
import com.javatar.ui.models.PluginRepositoryModel
import javafx.geometry.Pos
import javafx.scene.control.ListView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import tornadofx.View
import tornadofx.label
import tornadofx.onChange
import tornadofx.vbox

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

class PluginRepository : View() {

    override val root: HBox by fxml("pluginsrepo.fxml")

    val pluginList: ListView<PluginInformation> by fxid()

    val infoPanel: VBox by fxid()

    val pluginRepository: PluginRepositoryModel by di()

    init {

        pluginList.cellCache {
            vbox {
                alignment = Pos.CENTER
                spacing = 10.0
                label("Name: ${it.pluginName}")
                label("Author: ${it.pluginAuthor}")
                label("Version: ${it.pluginVersion}")
                label("Plugin: ${if (it.pluginEnabled) "Enabled" else "Disabled"}")
            }
        }

        pluginList.selectionModel.selectedItemProperty().onChange {
            if (it != null) {
                pluginRepository.manager.getExtensions(
                    PluginPanelExtension::class.java,
                    it.pluginName
                ).forEach { ext -> ext.createPluginInformationPanel(infoPanel) }
            }
        }

        pluginList.itemsProperty().bind(pluginRepository.plugins)

    }

}
