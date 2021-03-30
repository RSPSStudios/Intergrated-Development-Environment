package com.javatar.plugin.definition.editor.ui.models

import com.javatar.api.fs.directories.RootDirectory
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

class MapPackerModel : ViewModel() {

    val landscapeFile = bind { SimpleStringProperty(this, "landscape_file") }
    val floorFile = bind { SimpleStringProperty(this, "floor_file") }
    val regionId = bind { SimpleStringProperty(this, "region_id") }

    val root = bind { SimpleObjectProperty<RootDirectory>(this, "root") }

}