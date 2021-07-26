package com.javatar.plugin.definition.editor.ui.editor.area.model

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.impl.AreaDefinition
import com.javatar.osrs.definitions.loaders.AreaLoader
import com.javatar.plugin.definition.editor.managers.ConfigDefinitionManager
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel
import tornadofx.onChange

class AreaEditorModel : ViewModel() {

    val areas = bind { SimpleListProperty<AreaDefinition>(this, "areas", FXCollections.observableArrayList()) }

    val selected = bind { SimpleObjectProperty<AreaDefinition>(this, "selected") }

    val cache = SimpleObjectProperty<CacheLibrary>(this, "cache")

    val name = bind { SimpleStringProperty(this, "name", "") }
    val targetName = bind { SimpleStringProperty(this, "target_name", "") }

    val spriteId = bind { SimpleIntegerProperty(this, "sprite_id", -1) }

    val spriteId2 = bind { SimpleIntegerProperty(this, "sprite_id2", -1) }

    val menuActions = bind { SimpleListProperty<String>(this, "menu_actions", FXCollections.observableArrayList()) }

    init {
        selected.onChange {
            if(it != null) {
                name.set(it.name)
                targetName.set(it.field3308)
                spriteId.set(it.spriteId)
                spriteId2.set(it.field3294)
                menuActions.setAll(it.field3298.toList())
            }
        }
    }

    init {
        cache.onChange {
            if (it != null) {
                loadAreas()
            }
        }
    }

    private fun loadAreas() {
        val areaDefs = ConfigDefinitionManager(AreaLoader())
        val cache = cache.get()
        if (cache != null) {
            val list = mutableListOf<AreaDefinition>()
            val areaIds = cache.index(2).archive(35)?.fileIds() ?: intArrayOf()
            for (areaId in areaIds) {
                val data = cache.data(2, 35, areaId)
                if (data != null) {
                    list.add(areaDefs.load(areaId, data))
                }
            }
            areas.setAll(list)
        }
    }

}