package com.javatar.ui.models

import com.javatar.api.fs.directories.RootDirectory
import com.javatar.ui.views.fs.EditorTabPane
import com.javatar.ui.views.fs.FileExplorerScope
import com.javatar.ui.views.fs.FileSystemView
import javafx.scene.control.Tab
import javafx.scene.layout.VBox
import tornadofx.ViewModel
import tornadofx.find

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 12 2021
 */

class EditorModel : ViewModel() {

    val editorPane = EditorTabPane()

    fun openFileExplorer(name: String, rootDir: RootDirectory) {
        val scope = FileExplorerScope(rootDir)
        val view = find<FileSystemView>(scope)
        val tab = Tab(name, VBox(scope.crumb, view.root))
        editorPane.tabs.add(tab)
    }

}
