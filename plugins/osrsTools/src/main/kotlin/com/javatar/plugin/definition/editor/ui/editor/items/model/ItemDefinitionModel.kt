package com.javatar.plugin.definition.editor.ui.editor.items.model

import com.javatar.osrs.definitions.impl.ItemDefinition
import com.javatar.osrs.definitions.sprites.ItemSpriteFactory
import javafx.beans.property.*
import javafx.collections.FXCollections
import javafx.scene.image.Image
import tornadofx.ItemViewModel
import tornadofx.toObservable

class ItemDefinitionModel : ItemViewModel<ItemDefinition>(ItemDefinition(-1)) {

    val id = bind { SimpleIntegerProperty(this, "id", -1) }
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

    val groundOptions = bind {
        SimpleListProperty(
            this,
            "groundOptions",
            FXCollections.observableArrayList("null", "null", "Take", "null", "null")
        )
    }
    val interfaceOptions = bind {
        SimpleListProperty<String>(
            this,
            "interfaceOptions",
            FXCollections.observableArrayList("null", "null", "null", "null", "Drop")
        )
    }

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

    val icon = SimpleObjectProperty<Image>(this, "icon")

    init {
        update(this.item)
    }

    fun hasModels(): Boolean {
        return maleModel0.get() != 0
                || maleModel1.get() != 0
                || maleModel2.get() != 0
                || maleHeadModel0.get() != 0
                || maleHeadModel1.get() != 0
                || femaleModel0.get() != 0
                || femaleModel1.get() != 0
                || femaleModel2.get() != 0
                || femaleHeadModel0.get() != 0
                || femaleHeadModel1.get() != 0
    }

    fun update(def: ItemDefinition) {
        id.set(def.id)
        name.set(def.name)
        resizeX.set(def.resizeX)
        resizeY.set(def.resizeY)
        resizeZ.set(def.resizeZ)
        stackable.set(def.stackable)
        xan2d.set(def.xan2d)
        yan2d.set(def.yan2d)
        zan2d.set(def.zan2d)
        cost.set(def.cost)
        isTradeable.set(def.isTradeable)
        isMembers.set(def.isMembers)
        inventoryModel.set(def.inventoryModel)
        if (def.colorFind != null) {
            colorFind.set(def.colorFind.map { it.toInt() }.toObservable())
            colorReplace.set(def.colorReplace.map { it.toInt() }.toObservable())
        }
        if (def.textureFind != null) {
            textureFind.set(def.textureFind.map { it.toInt() }.toObservable())
            textureReplace.set(def.textureReplace.map { it.toInt() }.toObservable())
        }
        zoom2d.set(def.zoom2d)
        yOffset2d.set(def.yOffset2d)
        xOffset2d.set(def.xOffset2d)
        ambient.set(def.ambient)
        contrast.set(def.contrast)
        if (def.countCo != null) {
            countCo.set(def.countCo.toList().toObservable())
            countObj.set(def.countObj.toList().toObservable())
        }
        if (def.options != null) {
            groundOptions.clear()
            def.options.forEach { s ->
                groundOptions.add(s ?: "null")
            }
        }
        if (def.interfaceOptions != null) {
            interfaceOptions.clear()
            def.interfaceOptions.forEach { s ->
                interfaceOptions.add(s ?: "null")
            }
        }
        maleModel0.set(def.maleModel0)
        maleModel1.set(def.maleModel1)
        maleModel2.set(def.maleModel2)
        maleOffset.set(def.maleOffset)
        maleHeadModel0.set(def.maleHeadModel)
        maleHeadModel1.set(def.maleHeadModel2)
        femaleModel0.set(def.femaleModel0)
        femaleModel1.set(def.femaleModel1)
        femaleModel2.set(def.femaleModel2)
        femaleHeadModel0.set(def.femaleHeadModel)
        femaleHeadModel1.set(def.femaleHeadModel2)
        femaleOffset.set(def.femaleOffset)
        notedID.set(def.notedID)
        notedTemplate.set(def.notedTemplate)
        team.set(def.team)
        shiftClickDropIndex.set(def.shiftClickDropIndex)
        boughtId.set(def.boughtId)
        boughtTemplateId.set(def.boughtTemplateId)
        placeholderId.set(def.placeholderId)
        placeHolderTemplateId.set(def.placeholderTemplateId)
        if (def.params != null) {
            itemParams.set(def.params.toObservable())
        }
    }

    override fun onCommit() {
        super.onCommit()
        item = ItemDefinition(id.get())
        item.name = name.get()
        item.resizeX = resizeX.get()
        item.resizeY = resizeY.get()
        item.resizeZ = resizeZ.get()
        item.stackable = stackable.get()
        item.xan2d = xan2d.get()
        item.yan2d = yan2d.get()
        item.zan2d = zan2d.get()
        item.cost = cost.get()
        item.isTradeable = isTradeable.get()
        item.isMembers = isMembers.get()
        item.inventoryModel = inventoryModel.get()
        item.colorFind = colorFind.get().map { it.toShort() }.toShortArray()
        item.colorReplace = colorReplace.get().map { it.toShort() }.toShortArray()
        item.textureFind = textureFind.get().map { it.toShort() }.toShortArray()
        item.textureReplace = textureReplace.get().map { it.toShort() }.toShortArray()
        item.zoom2d = zoom2d.get()
        item.yOffset2d = yOffset2d.get()
        item.xOffset2d = xOffset2d.get()
        item.ambient = ambient.get()
        item.contrast = contrast.get()
        item.countCo = countCo.get().toIntArray()
        item.countObj = countObj.get().toIntArray()

        item.options = Array(groundOptions.size) {
            if (groundOptions[it] == "null") {
                null
            } else groundOptions[it]
        }
        item.interfaceOptions = Array(interfaceOptions.size) {
            if (interfaceOptions[it] == "null") {
                null
            } else interfaceOptions[it]
        }

        item.maleModel0 = maleModel0.get()
        item.maleModel1 = maleModel1.get()
        item.maleModel2 = maleModel2.get()
        item.maleOffset = maleOffset.get()
        item.maleHeadModel = maleHeadModel0.get()
        item.maleHeadModel2 = maleHeadModel1.get()
        item.femaleModel0 = femaleModel0.get()
        item.femaleModel1 = femaleModel1.get()
        item.femaleModel2 = femaleModel2.get()
        item.femaleOffset = femaleOffset.get()
        item.femaleHeadModel = femaleHeadModel0.get()
        item.femaleHeadModel2 = femaleHeadModel1.get()
        item.notedID = notedID.get()
        item.notedTemplate = notedTemplate.get()
        item.team = team.get()
        item.shiftClickDropIndex = shiftClickDropIndex.get()
        item.boughtId = boughtId.get()
        item.boughtTemplateId = boughtTemplateId.get()
        item.placeholderId = placeholderId.get()
        item.placeholderTemplateId = placeHolderTemplateId.get()
        item.params = itemParams.get()
    }
}