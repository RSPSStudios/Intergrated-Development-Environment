package com.javatar.ui.views

import com.javatar.api.http.Client
import com.javatar.api.http.DownloadStatus
import com.javatar.api.http.PluginInformationList
import com.javatar.api.ui.PluginPanelExtension
import com.javatar.api.ui.dialogs.ProgressDialog
import com.javatar.api.ui.models.AccountModel
import com.javatar.ui.data.PluginInformation
import com.javatar.ui.models.PluginRepositoryModel
import io.ktor.util.*
import javafx.beans.binding.Bindings
import javafx.geometry.Pos
import javafx.scene.control.ListView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.javafx.JavaFx
import tornadofx.*
import java.io.File

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

@InternalAPI
class PluginRepository : View() {

    override val root: HBox by fxml("pluginsrepo.fxml")

    val pluginList: ListView<PluginInformation> by fxid()

    val infoPanel: VBox by fxid()

    val pluginRepository: PluginRepositoryModel by di()

    val client: Client by di()

    val accountModel: AccountModel by inject()

    init {

        pluginList.cellCache { plugin ->
            vbox {
                alignment = Pos.CENTER
                spacing = 10.0
                label("Name: ${plugin.pluginName}")
                label("Author: ${plugin.pluginAuthor}")
                label("Version: ${plugin.pluginVersion}")
                label("Plugin: ${if (plugin.pluginEnabled) "Enabled" else "Disabled"}")
                button {
                    text = when {
                        plugin.updateAvailable -> "Update"
                        plugin.isInstalled -> "Uninstall"
                        else -> "Download"
                    }
                    action {
                        if (accountModel.activeCredentials.get() != null && (plugin.updateAvailable || !plugin.isInstalled)) {
                            val dir = app.config["PLUGIN_DIR"].toString()
                            val pluginZip = File(dir, "${plugin.pluginName}.zip")
                            val pluginDir = File(dir, plugin.pluginName)
                            if (pluginZip.exists()) {
                                pluginZip.delete()
                            }
                            if (pluginDir.exists() && pluginDir.isDirectory) {
                                pluginDir.deleteRecursively()
                            }
                            val prog = find<ProgressDialog>(Scope())
                            client.downloadFile(
                                "http://127.0.0.1:8080/tools/plugin/${plugin.pluginName}",
                                File(dir, "${plugin.pluginName}.zip"),
                                accountModel.activeCredentials.get()
                            ).onStart {
                                prog.openModal()
                            }.filterIsInstance<DownloadStatus.Progress>().onEach {
                                println(it.progress)
                                prog.progressModel.progress.set(it.progress)
                            }.onCompletion {
                                prog.close()
                                val id = pluginRepository.manager.loadPlugin(
                                    File(
                                        dir,
                                        "${plugin.pluginName}.zip"
                                    ).toPath()
                                )
                                pluginRepository.manager.startPlugin(id)
                            }.flowOn(Dispatchers.JavaFx).launchIn(CoroutineScope(Dispatchers.IO))
                        }
                    }
                }
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

        pluginList.itemsProperty().bind(Bindings.createObjectBinding({
            pluginRepository.plugins.values.toList().toObservable()
        }, pluginRepository.plugins))

    }

    @FlowPreview
    @InternalAPI
    override fun onBeforeShow() {
        super.onBeforeShow()

        val credentials = accountModel.activeCredentials.get()

        if (credentials != null) {
            client.get<PluginInformationList>("tools/plugins", credentials)
                .flatMapConcat { it.plugins.asFlow() }
                .onEach { listedPlugin ->
                    val found = pluginRepository.plugins[listedPlugin.name]
                    if (found != null && found.pluginVersion != listedPlugin.version) {
                        pluginRepository.plugins[listedPlugin.name] = found.copy(updateAvailable = true)
                    } else if (found == null) {
                        pluginRepository.plugins[listedPlugin.name] = PluginInformation(
                            listedPlugin.name,
                            listedPlugin.provider,
                            listedPlugin.description,
                            listedPlugin.version,
                            false,
                            false,
                            false
                        )
                    }
                }
                .launchIn(CoroutineScope(Dispatchers.JavaFx))
        }

    }
}
