package com.javatar.ui.views.plugins

import com.javatar.api.nexus.NexusClient
import com.javatar.ui.models.NexusComponentModel
import javafx.beans.binding.Bindings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.javafx.JavaFx
import tornadofx.View
import tornadofx.c
import tornadofx.style
import tornadofx.tabpane

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

class PluginRepository : View("Plugin Manager") {

    val client: NexusClient by di()
    val model: NexusComponentModel by inject()

    init {
        loadAvailablePlugins()
        model.downloadURL.bind(Bindings.createStringBinding({
            "${client.baseDownloadURL}/${model.repository.get()}/${model.name.get()}"
        }, model.repository, model.name, model.selectedAvailablePlugin))
    }

    override val root = tabpane {
        prefWidth = 950.0
        prefHeight = 700.0
        style {
            baseColor = c("#3f474f")
        }
        tab<InstalledPluginFragment>()
        tab<PluginComponentFragment>()
        tab<PluginRepositoryFragment>()
    }

    private fun loadAvailablePlugins() {
        client.getComponentList("rsps-studio-plugins")
            .onEach {
                model.availablePlugins.setAll(it.items)
            }.launchIn(CoroutineScope(Dispatchers.JavaFx))
    }
}
