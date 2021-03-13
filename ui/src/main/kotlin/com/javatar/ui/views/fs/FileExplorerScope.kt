package com.javatar.ui.views.fs

import com.javatar.fs.directories.RootDirectory
import com.javatar.ui.models.ActiveDirectoryModel
import javafx.collections.FXCollections
import tornadofx.Scope
import tornadofx.onChange

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 12 2021
 */

class FileExplorerScope(rootDirectory: RootDirectory) : Scope() {

    val activeDirectoryModel = ActiveDirectoryModel().apply {
        root.onChange { rootDir ->
            indexDir.set(null)
            archiveDir.set(null)
            activeNodes.set(
                FXCollections.observableArrayList(
                    rootDir?.nodes()?.map { FileSystemViewMeta(it.nodeIndex, FileSystemViewMeta.MetaType.INDEX) })
            )
        }
        root.set(rootDirectory)
    }

}
