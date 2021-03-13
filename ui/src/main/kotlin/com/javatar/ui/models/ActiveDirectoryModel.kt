package com.javatar.ui.models

import com.javatar.fs.directories.ArchiveDirectory
import com.javatar.fs.directories.IndexDirectory
import com.javatar.fs.directories.RootDirectory
import com.javatar.ui.views.fs.FileSystemViewMeta
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import tornadofx.ItemViewModel

class ActiveDirectory(
    val rootDirectory: RootDirectory,
    val indexDirectory: IndexDirectory,
    val archiveDirectory: ArchiveDirectory
)

class ActiveDirectoryModel : ItemViewModel<ArchiveDirectory>() {

    val cacheConfigurationModel: CacheConfigurationModel by di()

    val root = bind {
        SimpleObjectProperty(this, "root_directory", RootDirectory(cacheConfigurationModel.customCache.get()))
    }
    val indexDir = bind {
        SimpleObjectProperty(this, "index_directory", IndexDirectory(config.int("INDEX_DIR", -1), root.get()))
    }
    val archiveDir = bind {
        SimpleObjectProperty(this, "archive_directory", ArchiveDirectory(config.int("ARCHIVE_DIR", -1), indexDir.get()))
    }

    val activeNodes = bind {
        SimpleListProperty(
            FXCollections.observableArrayList(
                cacheConfigurationModel.customCache.get().indices()
                    .map { FileSystemViewMeta(it.id, FileSystemViewMeta.MetaType.INDEX) })
        )
    }

    override fun onCommit() {
        if (root.get() != null) {
            with(config) {
                if (indexDir.get() != null) {
                    set("INDEX_DIR" to indexDir.get().nodeIndex)
                    set("ARCHIVE_DIR" to archiveDir.get().id)
                    save()
                }
            }
        }
    }
}
