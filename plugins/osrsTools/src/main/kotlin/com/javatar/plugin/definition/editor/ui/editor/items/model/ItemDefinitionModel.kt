package com.javatar.plugin.definition.editor.ui.editor.items.model

import javafx.beans.property.*
import javafx.collections.FXCollections
import tornadofx.ViewModel

class ItemDefinitionModel : ViewModel() {

    val name = bind { SimpleStringProperty(this, "name", "null") }
    val resizeX = bind { SimpleIntegerProperty(this, "resizeX", 128) }
    val resizeY = bind { SimpleIntegerProperty(this, "resizeY", 128) }
    val resizeZ = bind { SimpleIntegerProperty(this, "resizeZ", 128) }

    val xan2d = bind { SimpleIntegerProperty(this, "xan2d", 0) }
    val yan2d = bind { SimpleIntegerProperty(this, "yan2d", 0) }
    val zan2d = bind { SimpleIntegerProperty(this, "zan2d", 0) }

    val cost = bind { SimpleIntegerProperty(this, "cost", 1) }

    val isTradeable = bind { SimpleBooleanProperty(this, "isTradeable", false) }
    val isMembers = bind { SimpleBooleanProperty(this, "isMembers", false) }

    val stackable = bind { SimpleIntegerProperty(this, "stackable", 0) }
    val inventoryModel = bind { SimpleIntegerProperty(this, "inventoryModel", 0) }

    val colorFind = bind { SimpleListProperty<Int>(this, "colorFind", FXCollections.observableArrayList()) }
    val colorReplace = bind { SimpleListProperty<Int>(this, "colorReplace", FXCollections.observableArrayList()) }
    val textureFind = bind { SimpleListProperty<Int>(this, "textureFind", FXCollections.observableArrayList()) }
    val textureReplace = bind { SimpleListProperty<Int>(this, "textureReplace", FXCollections.observableArrayList()) }

    val zoom2d = bind { SimpleIntegerProperty(this, "zoom2d", 2000) }
    val xOffset2d = bind { SimpleIntegerProperty(this, "xOffset2d", 0) }
    val yOffset2d = bind { SimpleIntegerProperty(this, "yOffset2d", 0) }
    val ambient = bind { SimpleIntegerProperty(this, "ambient", 0) }
    val contrast = bind { SimpleIntegerProperty(this, "contrast", 0) }

    val countCo = bind { SimpleListProperty<Int>(this, "countCo", FXCollections.observableArrayList()) }
    val countObj = bind { SimpleListProperty<Int>(this, "countObj", FXCollections.observableArrayList()) }

    val groundOptions = bind { SimpleListProperty<String>(this, "groundOptions", FXCollections.observableArrayList(null, null, "Take", null, null)) }
    val interfaceOptions = bind { SimpleListProperty<String>(this, "interfaceOptions", FXCollections.observableArrayList(null, null, null, null, "Drop")) }

    val maleModel0 = bind { SimpleIntegerProperty(this, "maleModel0", -1) }
    val maleModel1 = bind { SimpleIntegerProperty(this, "maleModel1", -1) }
    val maleModel2 = bind { SimpleIntegerProperty(this, "maleModel2", -1) }
    val maleOffset = bind { SimpleIntegerProperty(this, "maleOffset", 0) }
    val maleHeadModel0 = bind { SimpleIntegerProperty(this, "maleHeadModel0", -1) }
    val maleHeadModel1 = bind { SimpleIntegerProperty(this, "maleHeadModel1", -1) }

    val femaleModel0 = bind { SimpleIntegerProperty(this, "femaleModel0", -1) }
    val femaleModel1 = bind { SimpleIntegerProperty(this, "femaleModel1", -1) }
    val femaleModel2 = bind { SimpleIntegerProperty(this, "femaleModel2", -1) }
    val femaleOffset = bind { SimpleIntegerProperty(this, "femaleOffset", 0) }
    val femaleHeadModel0 = bind { SimpleIntegerProperty(this, "femaleHeadModel0", -1) }
    val femaleHeadModel1 = bind { SimpleIntegerProperty(this, "femaleHeadModel1", -1) }

    val notedID = bind { SimpleIntegerProperty(this, "notedID", -1) }
    val notedTemplate = bind { SimpleIntegerProperty(this, "notedTemplate", -1) }
    val team = bind { SimpleIntegerProperty(this, "team", 0) }
    val shiftClickDropIndex = bind { SimpleIntegerProperty(this, "shiftClickDropIndex", -2) }
    val boughtId = bind { SimpleIntegerProperty(this, "boughtId", -1) }
    val boughtTemplateId = bind { SimpleIntegerProperty(this, "boughtTemplateId", -1) }
    val placeholderId = bind { SimpleIntegerProperty(this, "placeholderId", -1) }
    val placeHolderTemplateId = bind { SimpleIntegerProperty(this, "placeHolderTemplateId", -1) }

    val itemParams = bind { SimpleMapProperty<Int, Any>(this, "params", FXCollections.observableHashMap()) }

}