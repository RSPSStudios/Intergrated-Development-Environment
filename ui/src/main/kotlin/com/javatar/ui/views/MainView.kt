package com.javatar.ui.views

import com.displee.cache.CacheLibrary
import com.javatar.fs.directories.RootDirectory
import com.javatar.ui.contextmenu
import com.javatar.ui.models.CacheConfigurationModel
import com.javatar.ui.models.EditorModel
import javafx.beans.binding.Bindings
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.TextInputDialog
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.layout.AnchorPane
import tornadofx.*

class MainView : View() {

    override val root: AnchorPane by fxml("main-view.fxml")

    val caches: TreeView<Pair<String, String>> by fxid()
    val editorView: AnchorPane by fxid()

    val configModel: CacheConfigurationModel by di()
    val editorModel: EditorModel by di()

    init {

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
}
