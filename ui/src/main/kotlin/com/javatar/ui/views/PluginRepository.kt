package com.javatar.ui.views

import com.javatar.ui.data.PluginInformation
import com.javatar.ui.models.PluginRepositoryModel
import javafx.geometry.Pos
import javafx.scene.control.ListView
import javafx.scene.layout.HBox
import tornadofx.*

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

class PluginRepository : View() {

    override val root: HBox by fxml("pluginsrepo.fxml")

    val pluginList: ListView<PluginInformation> by fxid()

    val pluginRepository: PluginRepositoryModel by di()

    init {

        pluginList.cellCache {
            vbox {
                alignment = Pos.CENTER
                spacing = 10.0
                label("Name: ${it.pluginName}")
                label("Author: ${it.pluginAuthor}")
                label("Version: ${it.pluginVersion}")
                button(if (it.pluginEnabled) "Enabled" else "Disabled").action {
                    pluginRepository.manager.disablePlugin(it.pluginName)
                }
            }
        }

        pluginList.itemsProperty().bind(pluginRepository.plugins)

    }

}
