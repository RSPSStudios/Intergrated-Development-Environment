package com.javatar.plugin.definition.editor.ui.editor.cvars.cvars

import com.javatar.osrs.definitions.impl.ClientVarDefinition
import com.javatar.osrs.definitions.loaders.ClientVariableLoader
import com.javatar.plugin.definition.editor.managers.ConfigDefinitionManager
import com.javatar.plugin.definition.editor.ui.editor.cvars.model.ClientPreferencesModel
import com.javatar.plugin.definition.editor.ui.editor.cvars.model.VariableModel
import javafx.scene.paint.Color
import tornadofx.*

class ClientPreferencesEditorFragment : Fragment("Client Preferences") {

    val varModel: VariableModel by inject()
    val prefModel: ClientPreferencesModel by inject()

    val prefs = ConfigDefinitionManager(ClientVariableLoader())

    override val root = listview(prefModel.preferences) {
        selectionModel.selectedItemProperty().onChange {
            prefModel.selected.set(it)
        }
        cellCache {
            text("Client Preference ${it.id}") {
                if (it.persisent) {
                    this.fill = Color.DARKRED
                }
            }
        }
    }

    fun loadPreferences() {
        val cache = varModel.cache.get()
        if (cache != null) {
            val prefIds = cache.index(2).archive(19)?.fileIds() ?: intArrayOf()
            val list = mutableListOf<ClientVarDefinition>()
            for (prefId in prefIds) {
                val data = cache.data(2, 19, prefId)
                if (data != null) {
                    list.add(prefs.load(prefId, data))
                }
            }
            prefModel.preferences.setAll(list)
        }
    }

}