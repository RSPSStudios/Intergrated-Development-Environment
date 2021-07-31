package com.javatar.ui

import com.javatar.api.nexus.NexusClient
import com.javatar.ui.data.PluginInformation
import com.javatar.ui.models.PluginRepositoryModel
import com.javatar.ui.views.MainView
import javafx.stage.Stage
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.pf4j.PluginState
import tornadofx.App
import java.nio.file.Files
import java.nio.file.Path

class EditorApplication : App(MainView::class), KoinComponent {

    val DEVELOPMENT_MODE = false
    val DEFAULT_PLUGIN_DIR = "${System.getProperty("user.home")}/rsps-studios/plugins"

    val pluginRepo: PluginRepositoryModel = get()
    val nexusClient: NexusClient = get()

    override fun init() {
        super.init()

        var pluginDir = config.string("PLUGIN_DIR")
        if (pluginDir == null) {
            pluginDir = DEFAULT_PLUGIN_DIR
            config["PLUGIN_DIR"] = pluginDir
        }
        val repositoryBaseURL = config["REPO_BASE_URL"]
        if(repositoryBaseURL == null) {
            config["REPO_BASE_URL"] = "http://legionkt.com:8085"
        }

        nexusClient.baseURL(config["REPO_BASE_URL"] as String)

        if (!DEVELOPMENT_MODE) {
            System.setProperty("pf4j.pluginsDir", pluginDir)
        } else {
            config["PLUGIN_DIR"] = "/home/javatar/IdeaProjects/Intergrated-Development-Environment/build/plugins"
            System.setProperty("pf4j.pluginsDir", config["PLUGIN_DIR"] as String)
        }


        if (!Files.exists(Path.of(System.getProperty("pf4j.pluginsDir")))) {
            Files.createDirectories(Path.of(System.getProperty("pf4j.pluginsDir")))
        }

        pluginRepo.services.set(hostServices)

        pluginRepo.manager.loadPlugins()
        pluginRepo.manager.startPlugins()

        println("Started ${pluginRepo.manager.plugins.size} Plugins @${pluginRepo.manager.pluginsRoots[0]}")

        pluginRepo.manager.plugins.forEach {
            pluginRepo.plugins[it.pluginId] = PluginInformation(
                it.descriptor.pluginId,
                it.descriptor.provider,
                it.descriptor.pluginDescription,
                it.descriptor.version,
                it.pluginState == PluginState.STARTED,
                false,
                true
            )
        }

        pluginRepo.calculateHashes()
    }

    override fun start(stage: Stage) {
        stage.sizeToScene()
        stage.isResizable = false
        super.start(stage)
    }
}
