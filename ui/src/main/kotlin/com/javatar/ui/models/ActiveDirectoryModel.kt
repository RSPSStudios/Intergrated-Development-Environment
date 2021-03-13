package com.javatar.ui.models

import com.javatar.fs.directories.ArchiveDirectory
import com.javatar.fs.directories.IndexDirectory
import com.javatar.fs.directories.RootDirectory
import com.javatar.ui.views.fs.FileSystemViewMeta
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

class ActiveDirectoryModel : ViewModel() {

    val root = bind {
        SimpleObjectProperty<RootDirectory>(this, "root_directory")
    }
    val indexDir = bind {
        SimpleObjectProperty<IndexDirectory>(this, "index_directory")
    }
    val archiveDir = bind {
        SimpleObjectProperty<ArchiveDirectory>(this, "archive_directory")
    }

    val activeNodes = bind {
        SimpleListProperty<FileSystemViewMeta>(
            FXCollections.observableArrayList()
        )
    }
}
