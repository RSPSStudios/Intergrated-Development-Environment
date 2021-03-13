package com.javatar.ui.views

import com.displee.cache.CacheLibrary
import com.javatar.api.fs.directories.RootDirectory
import com.javatar.api.ui.MenuItemExtension
import com.javatar.api.ui.ToolTabExtension
import com.javatar.api.ui.utilities.contextmenu
import com.javatar.ui.models.CacheConfigurationModel
import com.javatar.ui.models.EditorModel
import com.javatar.ui.models.PluginRepositoryModel
import javafx.beans.binding.Bindings
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import tornadofx.*
import java.awt.Desktop
import java.io.File
import java.nio.file.Files
import java.nio.file.Path


class MainView : View() {

    override val root: AnchorPane by fxml("main-view.fxml")

    val caches: TreeView<Pair<String, String>> by fxid()
    val editorView: AnchorPane by fxid()
    val menuBar: MenuBar by fxid()
    val pluginsMenu: Menu by fxid()
    val toolTabs: TabPane by fxid()

    val configModel: CacheConfigurationModel by di()
    val editorModel: EditorModel by di()

    val pluginRepository: PluginRepositoryModel by di()

    init {

        pluginRepository.manager.getExtensions(MenuItemExtension::class.java)
            .forEach { it.createMenuItem(pluginsMenu, menuBar) }

        println(pluginRepository.manager.getExtensions(ToolTabExtension::class.java).size)

        pluginRepository.manager.getExtensions(ToolTabExtension::class.java)
            .forEach {
                println("Creating tab")
                it.createToolTab(toolTabs)
            }

        editorView.add(editorModel.editorPane)

        caches.cellFormat {
            text = item.first
            if (item.first == "Caches") {
                contextmenu {
                    item("Open Cache").action {
                        loadCache()
                    }
                }
            } else {
                contextmenu {
                    val value = item
                    item("Load").action {
                        configModel.activeCache.set(CacheLibrary.create(value.second))
                        editorModel.openFileExplorer(value.first, RootDirectory(configModel.activeCache.get()))
                    }
                    item("Remove").action {
                        configModel.cachePaths.remove(value.first)
                        caches.root.children.remove(treeItem)
                    }
                }
            }
        }

        caches.root = TreeItem("Caches" to "")

        caches.rootProperty().bind(Bindings.createObjectBinding({
            TreeItem("Caches" to "").also {
                it.children.setAll(configModel.cachePaths.map { entry ->
                    TreeItem(entry.toPair())
                })
            }
        }, configModel.cachePaths))
    }

    @FXML
    fun loadCache() {
        val dir = chooseDirectory("Choose Cache Directory")
        if (dir != null && dir.exists()) {
            val nameDialog = TextInputDialog(dir.nameWithoutExtension)
            configModel.cacheName.bind(nameDialog.editor.textProperty())
            configModel.cacheName.addValidator(nameDialog.editor) {
                if (caches.root.children.find { it.value.first == nameDialog.editor.text } != null) {
                    error("Cache already opened.")
                } else success("Cache successfully opened.")
            }
            nameDialog.showAndWait()
            configModel.activeCache.set(CacheLibrary.create(dir.absolutePath))
            configModel.cachePaths[nameDialog.editor.text] = dir.absolutePath
        } else {
            alert(Alert.AlertType.ERROR, "Could not find cache.").show()
        }
    }

    @FXML
    fun pluginRepo() {
        find<PluginRepository>().openModal()
    }

    @FXML
    fun openPluginLocation() {
        val location = config.string("pf4j.pluginsDir", System.getProperty("pf4j.pluginsDir"))
        val path = Path.of(location)
        if (!Files.exists(path)) {
            Files.createDirectory(path)
        }
        if (Desktop.isDesktopSupported()) {
            val desktop = Desktop.getDesktop()
            desktop.browse(File(location).toURI())
        }
    }

    @FXML
    fun setPluginLocation() {
        val dir = chooseDirectory("Choose plugin location")
        with(config) {
            if (dir != null && dir.exists() && dir.isDirectory) {
                set("pf4j.pluginsDir", dir.absolutePath)
                save()
            }
        }
    }

}
