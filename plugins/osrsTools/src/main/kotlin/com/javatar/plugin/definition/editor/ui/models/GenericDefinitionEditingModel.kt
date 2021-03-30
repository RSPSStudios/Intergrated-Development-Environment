package com.javatar.plugin.definition.editor.ui.models

import com.javatar.api.fs.FileType
import com.javatar.api.fs.JFile
import com.javatar.api.fs.directories.RootDirectory
import com.javatar.api.fs.files.DataFile
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

class GenericDefinitionEditingModel : ViewModel() {

    val json = bind { SimpleStringProperty(this, "json") }

    val last_export = bind { SimpleStringProperty(this, "last_export", config.string("last_export")) }

    val fileNode = bind { SimpleObjectProperty<JFile>(this, "file_node") }

    val fileType = bind { SimpleObjectProperty<FileType>(this, "file_type") }

    val root = bind { SimpleObjectProperty<RootDirectory>(this, "root") }

    override fun onCommit() {
        super.onCommit()
        with(config) {
            if (last_export.get() != null) {
                set("last_export", last_export.get())
                save()
            }
        }
    }
}