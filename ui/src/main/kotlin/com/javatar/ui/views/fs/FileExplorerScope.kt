package com.javatar.ui.views.fs

import com.javatar.api.fs.directories.IndexDirectory
import com.javatar.api.fs.directories.RootDirectory
import com.javatar.ui.Crumb
import com.javatar.ui.models.ActiveDirectoryModel
import javafx.collections.FXCollections
import javafx.scene.control.TabPane
import org.controlsfx.control.BreadCrumbBar
import tornadofx.Scope
import tornadofx.onChange

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 12 2021
 */

class FileExplorerScope(rootDirectory: RootDirectory, val editorTab: TabPane) : Scope() {

    val crumb = BreadCrumbBar<Crumb>()

    init {
        crumb.setOnCrumbAction {
            it.selectedCrumb.value.action()
        }
    }

    val activeDirectoryModel = ActiveDirectoryModel().apply {
        root.onChange { rootDir ->
            if (rootDir != null) {
                backToRoot(this, rootDir)
            }
        }
        indexDir.onChange {
            if (it != null) {
                crumb.selectedCrumb = BreadCrumbBar.buildTreeModel(
                    Crumb("Root") {
                        backToRoot(this, root.get())
                    },
                    Crumb("Index ${it.nodeIndex}")
                )
            }
        }
        archiveDir.onChange {
            if (it != null) {
                crumb.selectedCrumb = BreadCrumbBar.buildTreeModel(
                    Crumb("Root") {
                        backToRoot(this, root.get())
                    },
                    Crumb("Index ${it.parent.nodeIndex}") {
                        backToIndex(this, it.parent)
                    },
                    Crumb("Archive ${it.id}")
                )
            }
        }
        root.set(rootDirectory)
    }

    private fun backToIndex(activeDirectoryModel: ActiveDirectoryModel, indexDirectory: IndexDirectory) {
        with(activeDirectoryModel) {
            archiveDir.set(null)
            activeNodes.set(
                FXCollections.observableArrayList(
                    indexDirectory.nodes().map { FileSystemViewMeta(it.id, FileSystemViewMeta.MetaType.ARCHIVE) })
            )
            crumb.selectedCrumb = BreadCrumbBar.buildTreeModel(Crumb("Root"))
        }
    }

    private fun backToRoot(activeDirectoryModel: ActiveDirectoryModel, rootDirectory: RootDirectory) {
        activeDirectoryModel.indexDir.set(null)
        activeDirectoryModel.archiveDir.set(null)
        activeDirectoryModel.activeNodes.set(
            FXCollections.observableArrayList(
                rootDirectory.nodes().map { FileSystemViewMeta(it.nodeIndex, FileSystemViewMeta.MetaType.INDEX) })
        )
        crumb.selectedCrumb = BreadCrumbBar.buildTreeModel(Crumb("Root"))
    }

}
