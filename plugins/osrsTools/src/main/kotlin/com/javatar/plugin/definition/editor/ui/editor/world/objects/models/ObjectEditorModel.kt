package com.javatar.plugin.definition.editor.ui.editor.world.objects.models

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.impl.ObjectDefinition
import javafx.beans.property.*
import javafx.collections.FXCollections
import tornadofx.ViewModel
import tornadofx.onChange

class ObjectEditorModel : ViewModel() {

    val objects = bind { SimpleListProperty<ObjectDefinition>(this, "objects", FXCollections.observableArrayList()) }
    val selected = bind { SimpleObjectProperty<ObjectDefinition>(this, "selected") }
    val searchText = bind { SimpleStringProperty(this, "search_text", "") }

    init {
        selected.onChange {
            if(it != null) {
                update(it)
            }
        }
    }

    val name = bind { SimpleStringProperty(this, "name", "null") }
    val textureReplacement =
        bind { SimpleListProperty<Int>(this, "texture_replacement", FXCollections.observableArrayList()) }
    val textureFind = bind { SimpleListProperty<Int>(this, "texture_find", FXCollections.observableArrayList()) }
    val decorDisplacement = bind { SimpleIntegerProperty(this, "decor_displacement", 16) }
    val isHollow = bind { SimpleBooleanProperty(this, "is_hollow", false) }
    val objectModels = bind { SimpleListProperty<Int>(this, "object_models", FXCollections.observableArrayList()) }
    val objectTypes = bind { SimpleListProperty<Int>(this, "object_types", FXCollections.observableArrayList()) }
    val colorFind = bind { SimpleListProperty<Int>(this, "color_find", FXCollections.observableArrayList()) }
    val colorReplacement =
        bind { SimpleListProperty<Int>(this, "color_replacement", FXCollections.observableArrayList()) }
    val mapAreaId = bind { SimpleIntegerProperty(this, "map_area_id", -1) }

    val sizeX = bind { SimpleIntegerProperty(this, "size_x", 1) }
    val sizeY = bind { SimpleIntegerProperty(this, "size_y", 1) }

    val ambientSoundID = bind { SimpleIntegerProperty(this, "ambient_sound_id", -1) }
    val soundEffectRadius = bind { SimpleIntegerProperty(this, "sound_effect_radius", 0) }
    val soundEffectIds = bind { SimpleListProperty<Int>(this, "sound_effect_ids", FXCollections.observableArrayList()) }

    val offsetX = bind { SimpleIntegerProperty(this, "offset_x", 0) }
    val offsetY = bind { SimpleIntegerProperty(this, "offset_y", 0) }
    val offsetHeight = bind { SimpleIntegerProperty(this, "offset_height", 0) }

    val mergeNormals = bind { SimpleBooleanProperty(this, "merge_normals", false) }
    val hasContextMenu = bind { SimpleIntegerProperty(this, "has_context_menu", -1) }
    val animationID = bind { SimpleIntegerProperty(this, "animation_id", -1) }

    val varbitID = bind { SimpleIntegerProperty(this, "varbit_id", -1) }
    val varpID = bind { SimpleIntegerProperty(this, "varp_id", -1) }
    val transformations = bind { SimpleListProperty<Int>(this, "transformations", FXCollections.observableArrayList()) }

    val ambient = bind { SimpleIntegerProperty(this, "ambient", 0) }
    val contrast = bind { SimpleIntegerProperty(this, "contrast", 0) }

    val actions = bind {
        SimpleListProperty<String>(
            this, "actions",
            FXCollections.observableArrayList()
        )
    }

    val interactType = bind { SimpleIntegerProperty(this, "interact_type", 2) }
    val mapSceneID = bind { SimpleIntegerProperty(this, "map_scene_id", -1) }

    val blockingMask = bind { SimpleIntegerProperty(this, "blocking_mask", 0) }

    val shadow = bind { SimpleBooleanProperty(this, "shadow", true) }

    val modelSizeX = bind { SimpleIntegerProperty(this, "model_size_x", 128) }
    val modelSizeY = bind { SimpleIntegerProperty(this, "model_size_y", 128) }
    val modelSizeHeight = bind { SimpleIntegerProperty(this, "model_size_height", 128) }

    val obstructsGround = bind { SimpleBooleanProperty(this, "obstructs_ground", false) }

    val contouredGround = bind { SimpleIntegerProperty(this, "contouredGround", -1) }
    val supportsItems = bind { SimpleIntegerProperty(this, "supports_items", -1) }

    val isRotated = bind { SimpleBooleanProperty(this, "is_rotated", false) }
    val blocksProjectile = bind { SimpleBooleanProperty(this, "blocks_projectile", true) }
    val randomizeAnimationStart = bind { SimpleBooleanProperty(this, "randomize_animation_start", false) }

    val parameters = bind { SimpleMapProperty<Int, Any>(this, "params", FXCollections.observableHashMap()) }

    val cache = SimpleObjectProperty<CacheLibrary>(this, "cache")

    fun update(def: ObjectDefinition) {
        name.set(def.name)
        if (def.textureToReplace != null && def.retextureToFind != null) {
            textureReplacement.setAll(def.textureToReplace.map { it.toInt() })
            textureFind.setAll(def.retextureToFind.map { it.toInt() })
        }
        decorDisplacement.set(def.decorDisplacement)
        isHollow.set(def.isHollow)
        if (def.objectModels != null) {
            objectModels.setAll(def.objectModels.toList())
        }
        if (def.objectTypes != null) {
            objectTypes.setAll(def.objectTypes.toList())
        }
        if (def.recolorToFind != null && def.recolorToReplace != null) {
            colorFind.setAll(def.recolorToFind.map { it.toInt() })
            colorReplacement.setAll(def.recolorToReplace.map { it.toInt() })
        }
        mapAreaId.set(def.mapAreaId)
        sizeX.set(def.sizeX)
        sizeY.set(def.sizeY)
        ambientSoundID.set(def.ambientSoundId)
        soundEffectRadius.set(def.soundEffectRadius)
        if (def.soundEffectIds != null) {
            soundEffectIds.setAll(*def.soundEffectIds.toTypedArray())
        }
        offsetX.set(def.offsetX)
        offsetY.set(def.offsetY)
        offsetHeight.set(def.offsetHeight)
        mergeNormals.set(def.isMergeNormals)
        hasContextMenu.set(def.wallOrDoor)
        animationID.set(def.animationID)
        varbitID.set(def.varbitID)
        varpID.set(def.varpID)
        if (def.configChangeDest != null) {
            transformations.setAll(*def.configChangeDest.toTypedArray())
        }
        ambient.set(def.ambient)
        contrast.set(def.contrast)
        if (def.actions != null) {
            actions.setAll(*def.actions)
        }
        interactType.set(def.interactType)
        mapSceneID.set(def.mapSceneID)
        blockingMask.set(def.blockingMask)
        shadow.set(def.isShadow)
        modelSizeX.set(def.modelSizeX)
        modelSizeY.set(def.modelSizeY)
        modelSizeHeight.set(def.modelSizeHeight)
        obstructsGround.set(def.isObstructsGround)
        contouredGround.set(def.contouredGround)
        supportsItems.set(def.supportsItems)
        isRotated.set(def.isRotated)
        blocksProjectile.set(def.isBlocksProjectile)
        randomizeAnimationStart.set(def.isRandomizeAnimStart)
        if (def.params != null) {
            parameters.putAll(def.params)
        }
    }

    fun commitObject() {
        val def = selected.get()
        if(def != null) {
            def.name = name.get()
            def.textureToReplace = textureReplacement.map { it.toShort() }.toShortArray()
            def.retextureToFind = textureFind.map { it.toShort() }.toShortArray()
            def.decorDisplacement = decorDisplacement.get()
            def.isHollow = isHollow.get()
            def.objectModels = objectModels.toIntArray()
            def.objectTypes = objectTypes.toIntArray()
            def.recolorToFind = colorFind.map { it.toShort() }.toShortArray()
            def.recolorToReplace = colorReplacement.map { it.toShort() }.toShortArray()
            def.mapAreaId = mapAreaId.get()
            def.sizeX = sizeX.get()
            def.sizeY = sizeY.get()
            def.ambientSoundId = ambientSoundID.get()
            def.soundEffectRadius = soundEffectRadius.get()
            def.soundEffectIds = soundEffectIds.toIntArray()
            def.offsetX = offsetX.get()
            def.offsetY = offsetY.get()
            def.offsetHeight = offsetHeight.get()
            def.isMergeNormals = mergeNormals.get()
            def.wallOrDoor = hasContextMenu.get()
            def.animationID = animationID.get()
            def.varbitID = varbitID.get()
            def.varpID = varpID.get()
            def.configChangeDest = transformations.toIntArray()
            def.ambient = ambient.get()
            def.contrast = contrast.get()
            def.actions = actions.toTypedArray()
            def.interactType = interactType.get()
            def.mapSceneID = mapSceneID.get()
            def.blockingMask = blockingMask.get()
            def.isShadow = shadow.get()
            def.modelSizeX = modelSizeX.get()
            def.modelSizeY = modelSizeY.get()
            def.modelSizeHeight = modelSizeHeight.get()
            def.isObstructsGround = obstructsGround.get()
            def.contouredGround = contouredGround.get()
            def.supportsItems = supportsItems.get()
            def.isRotated = isRotated.get()
            def.isBlocksProjectile = blocksProjectile.get()
            def.isRandomizeAnimStart = randomizeAnimationStart.get()
            def.params = mutableMapOf()
            def.params.putAll(parameters)
        }
    }

}