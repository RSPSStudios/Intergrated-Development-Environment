package com.javatar.plugin.definition.editor.ui.editor.cvars.model

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.impl.VarbitDefinition
import com.javatar.osrs.definitions.impl.VarpDefinition
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.ui.editor.cvars.VariableEditingType
import javafx.beans.property.*
import javafx.collections.FXCollections
import javafx.scene.control.Button
import javafx.scene.control.Control
import javafx.scene.control.ToolBar
import tornadofx.ViewModel
import tornadofx.onChange

class VariableModel : ViewModel() {

    val variables = bind { SimpleMapProperty<VarpDefinition, SimpleListProperty<VarbitDefinition>>(this, "variables", FXCollections.observableHashMap()) }

    val varps = bind { SimpleListProperty<VarpDefinition>(this, "varps", FXCollections.observableArrayList()) }

    val unusedVarps = bind { SimpleListProperty<VarpDefinition>(this, "unused_varps", FXCollections.observableArrayList()) }

    val varbits = bind { SimpleListProperty<VarbitDefinition>(this, "varbits", FXCollections.observableArrayList()) }

    val selectedVarp = bind { SimpleObjectProperty<VarpDefinition>(this, "selected_varp") }

    val selectedVarbit = bind { SimpleObjectProperty<VarbitDefinition>(this, "selected_varbit") }

    val maxVarpSize = bind { SimpleIntegerProperty(this, "max_varp_size", config["max_varp_size"] as Int? ?: 4000) }

    val cache = SimpleObjectProperty<CacheLibrary>(this, "cache")

    val editingType = SimpleObjectProperty(this, "editing_type", VariableEditingType.PLAYER_VARIABLE)

    init {
        maxVarpSize.onChange {
            config["max_varp_size"] = it
            config.save()
        }
    }

    fun loadVariables() {
        loadVarps()
        val varbitList = loadVarbits()

        varps.forEach { varp ->
            varbitList.forEach {
                if(it.index == varp.definitionId) {
                    val list = variables.getOrPut(varp) {
                        SimpleListProperty(FXCollections.observableArrayList())
                    }
                    list.add(it)
                }
            }
        }

        selectedVarp.onChange {
            if(it != null) {
                val list = variables[it]
                if (list != null && list.isNotEmpty()) {
                    varbits.setAll(list)
                } else {
                    varbits.clear()
                }
            } else {
                varbits.clear()
            }
        }

        findUnusedVarps()

    }

    fun loadVarps() {
        val cache = cache.get()
        val varps = OldSchoolDefinitionManager.varps
        if(cache != null) {
            val varpIds = cache.index(2).archive(16)?.fileIds() ?: intArrayOf()
            val list = mutableListOf<VarpDefinition>()
            for (varpId in varpIds) {
                val data = cache.data(2, 16, varpId)
                if(data != null) {
                    list.add(varps.load(varpId, data))
                }
            }
            this.varps.setAll(list)
        }
    }

    fun findUnusedVarps() {
        val list = mutableListOf<VarpDefinition>()
        val varps = OldSchoolDefinitionManager.varps

        varps.definitions.values.forEach {
            if(!variables.containsKey(it) && it.type == 0) {
                list.add(it)
            }
        }

        unusedVarps.setAll(list)
    }

    fun loadVarbits() : List<VarbitDefinition> {
        val cache = cache.get()
        val varbits = OldSchoolDefinitionManager.varbits
        if(cache != null) {
            val varbitIds = cache.index(2).archive(14)?.fileIds() ?: intArrayOf()
            val list = mutableListOf<VarbitDefinition>()
            for (varbitId in varbitIds) {
                val data = cache.data(2, 14, varbitId)
                if(data != null) {
                    list.add(varbits.load(varbitId, data))
                }
            }
            return list
        }
        return emptyList()
    }

}