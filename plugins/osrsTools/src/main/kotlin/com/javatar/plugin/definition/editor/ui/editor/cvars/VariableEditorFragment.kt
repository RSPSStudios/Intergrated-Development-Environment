package com.javatar.plugin.definition.editor.ui.editor.cvars

import com.javatar.api.http.Client
import com.javatar.api.http.StringBody
import com.javatar.api.ui.models.AccountModel
import com.javatar.osrs.definitions.impl.ClientVarDefinition
import com.javatar.osrs.definitions.impl.VarbitDefinition
import com.javatar.osrs.definitions.impl.VarpDefinition
import com.javatar.osrs.definitions.loaders.ClientVariableLoader
import com.javatar.osrs.definitions.loaders.VarbitLoader
import com.javatar.osrs.definitions.loaders.VarpLoader
import com.javatar.osrs.tools.variables.VariableTools
import com.javatar.osrs.tools.variables.VariableTools.bitCount
import com.javatar.plugin.definition.editor.OsrsDefinitionEditor.Companion.gson
import com.javatar.plugin.definition.editor.managers.ConfigDefinitionManager
import com.javatar.plugin.definition.editor.ui.NumberSpinnerValueFactory
import com.javatar.plugin.definition.editor.ui.editor.cvars.cvars.ClientPreferencesEditorFragment
import com.javatar.plugin.definition.editor.ui.editor.cvars.model.*
import com.javatar.plugin.definition.editor.ui.editor.cvars.varps.UnusedVarpsFragment
import com.javatar.plugin.definition.editor.ui.editor.cvars.varps.VarpsFragment
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import javafx.event.EventTarget
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.Control
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.javafx.JavaFx
import tornadofx.*

class VariableEditorFragment : Fragment("Variable Player Editor") {

    val varModel: VariableModel by inject()
    val varpModel: VarpModel by inject()
    val varbitModel: VarbitModel by inject()
    val prefModel: ClientPreferencesModel by inject()

    val account: AccountModel by di()
    val client: Client by di()

    val varps = ConfigDefinitionManager(VarpLoader())
    val varbits = ConfigDefinitionManager(VarbitLoader())
    val preferences = ConfigDefinitionManager(ClientVariableLoader())

    init {
        varModel.selectedVarp.onChange {
            if (it != null) {
                varpModel.id.set(it.definitionId)
                varpModel.type.set(it.type)
            }
        }
        varModel.selectedVarbit.onChange {
            if (it != null) {
                varbitModel.id.set(it.id)
                varbitModel.varpId.set(it.index)
                varbitModel.lsb.set(it.leastSignificantBit)
                varbitModel.msb.set(it.mostSignificantBit)
                when (val bitCount = it.mostSignificantBit - it.leastSignificantBit) {
                    0 -> {
                        varbitModel.variableType.set(VariableType.BOOLEAN)
                        varbitModel.maxValue.set(1)
                    }
                    8 -> {
                        varbitModel.variableType.set(VariableType.BYTE)
                        varbitModel.maxValue.set(255)
                    }
                    16 -> {
                        varbitModel.variableType.set(VariableType.SHORT)
                        varbitModel.maxValue.set(65535)
                    }
                    32 -> {
                        varbitModel.variableType.set(VariableType.INT)
                        varbitModel.maxValue.set(Int.MAX_VALUE)
                    }
                    else -> {
                        varbitModel.variableType.set(VariableType.CUSTOM)
                        varbitModel.maxValue.set((1 shl bitCount))
                    }
                }
            }
        }
    }

    override val root = vbox {
        style = "-fx-base: #3f474f;"
        prefHeight = 415.0
        toolbar {
            dynamicContent(varModel.editingType) {
                when (it) {
                    VariableEditingType.PLAYER_VARIABLE -> items.setAll(*varpAndVarbitToolBar())
                    VariableEditingType.CLIENT_PREFERENCES -> items.setAll(*clientPreferencesToolbar())
                    null -> {
                        items.clear()
                    }
                }
            }
        }
        hbox {
            spacing = 10.0

            drawer {
                item<VarpsFragment> {
                    expandedProperty.onChange {
                        if (it) {
                            varModel.editingType.set(VariableEditingType.PLAYER_VARIABLE)
                        }
                    }
                    expanded = true
                }
                item<UnusedVarpsFragment>() {
                    expandedProperty.onChange {
                        if (it) {
                            varModel.editingType.set(VariableEditingType.PLAYER_VARIABLE)
                        }
                    }
                }
                item<ClientPreferencesEditorFragment>() {
                    expandedProperty.onChange {
                        if (it) {
                            find<ClientPreferencesEditorFragment> {
                                loadPreferences()
                            }
                            varModel.editingType.set(VariableEditingType.CLIENT_PREFERENCES)
                        }
                    }
                }
            }

            form {
                dynamicContent(varModel.editingType) {
                    when (it) {
                        VariableEditingType.PLAYER_VARIABLE -> buildPlayerVariableUI()
                        VariableEditingType.CLIENT_PREFERENCES -> buildClientVariableUI()
                        null -> {
                        }
                    }
                }
            }
            listview(varModel.varbits) {
                varModel.selectedVarbit.bind(selectionModel.selectedItemProperty())
                cellFormat {
                    text = "Varbit ${it.id}"
                }
            }
        }
    }

    private fun EventTarget.buildClientVariableUI() {
        fieldset("Configs") {
            field("Persistent") {
                checkbox(property = prefModel.persistent)
            }
        }
        fieldset("Information") {
            label("These preferences are saved to the preferences.dat file.")
            label("The preferences.dat file is located in the cache directory.")
        }
    }

    private fun EventTarget.buildPlayerVariableUI() {
        fieldset("Varp Configs") {
            disableWhen(varModel.selectedVarp.isNull)
            field("Type") {
                spinner(property = varpModel.type, editable = true, min = 0, max = 3999) {
                    editor.stripNonNumeric()
                }
            }
        }
        fieldset("Varbit Config") {
            disableWhen(varModel.selectedVarbit.isNull)
            field("Varp ID") {
                spinner(property = varbitModel.varpId, editable = true, min = 0, max = Int.MAX_VALUE) {
                    editor.stripNonNumeric()
                }
            }
            field("LSB") {
                val factory = NumberSpinnerValueFactory(0, 32)
                spinner(factory) {
                    factory.valueProperty().bindBidirectional(varbitModel.lsb)
                    factory.maxProperty.bind(Bindings.createIntegerBinding({
                        val value = varbitModel.maxValue.get()
                        val requiredBits = value.bitCount()
                        32 - requiredBits
                    }, varbitModel.lsb, varbitModel.maxValue))
                }
            }
            field("MSB") {
                label(varbitModel.msb)
            }
        }
        fieldset("Varbit Value Information") {
            disableWhen(varModel.selectedVarbit.isNull)
            field("Variable Type") {
                hbox {
                    spacing = 5.0
                    dynamicContent(varbitModel.variableType) {
                        choicebox(varbitModel.variableType, VariableType.values().toList())
                        if (it != null && it === VariableType.CUSTOM) {
                            spinner(
                                property = varbitModel.maxValue,
                                editable = true,
                                min = 0,
                                max = Int.MAX_VALUE
                            ) {
                                editor.stripNonNumeric()
                            }
                        } else {
                            textfield(varbitModel.maxValue) {
                                isEditable = false
                            }
                        }
                    }
                }
            }
        }
        fieldset("Information") {
            spacing = 10.0
            label("Warning: Old School hard codes the maximum varp count to 4000")
            label {
                textProperty().bind(Bindings.createStringBinding({
                    "Your maximum varp count is ${varModel.maxVarpSize.get()}"
                }, varModel.maxVarpSize))
            }
            label {
                textProperty().bind(Bindings.createStringBinding({
                    val used = varModel.varps.size
                    "There are ${varModel.maxVarpSize.get() - used} remaining unused varps."
                }, varModel.maxVarpSize, varModel.varps))
            }
        }
    }

    private fun deleteVarpAndVarbits(varpDefinition: VarpDefinition) {
        val cache = varModel.cache.get()
        if (cache != null) {
            val ids = mutableListOf<Int>()
            val varbitIds = cache.index(2).archive(14)?.fileIds() ?: intArrayOf()
            for (varbitId in varbitIds) {
                val data = cache.data(2, 14, varbitId)
                if (data != null) {
                    val def = varbits.load(varbitId, data)
                    if (def.index == varpDefinition.definitionId) {
                        ids.add(def.id)
                    }
                }
            }
            ids.forEach { id ->
                cache.remove(2, 14, id)
            }
            cache.remove(2, 16, varpDefinition.definitionId)
            cache.index(2).update()
        }
    }

    private fun clientPreferencesToolbar(): Array<Control> = arrayOf(
        button("Pack Preference") {
            disableWhen(prefModel.selected.isNull)
            action {
                val cache = varModel.cache.get()
                val creds = account.activeCredentials.get()
                if (cache != null && creds != null) {
                    prefModel.commitPreference()
                    val def = prefModel.selected.get()
                    val json = gson.toJson(def)
                    client.post<ByteArray>("tools/osrs/cvars", StringBody(json), creds)
                        .catch {
                            alert(
                                Alert.AlertType.ERROR,
                                "Error Packing Client Preference",
                                it.message
                            )
                            emit(byteArrayOf())
                        }
                        .onEach {
                            if (it.isNotEmpty()) {
                                cache.put(2, 19, def.id, it)
                                cache.index(2).update()
                                alert(
                                    Alert.AlertType.INFORMATION,
                                    "Packing Client Preference",
                                    "Successfully packed client preference."
                                )
                            }
                        }.launchIn(CoroutineScope(Dispatchers.JavaFx))
                }
            }
        },
        button("Add Preference") {
            action {
                val newDef = ClientVarDefinition(preferences.nextId)
                val confirm = alert(
                    Alert.AlertType.CONFIRMATION,
                    "New Client Preference",
                    "Would you like to make this preference persistent?",
                    buttons = arrayOf(ButtonType.YES, ButtonType.NO)
                )
                newDef.persisent = confirm.result === ButtonType.YES
                preferences.add(newDef)
                prefModel.preferences.add(newDef)
            }
        },
        button("Remove Preference") {
            disableWhen(prefModel.selected.isNull)
            action {
                val cache = varModel.cache.get()
                if (cache != null) {
                    val pref = prefModel.selected.get()
                    prefModel.preferences.remove(pref)
                    preferences.remove(pref)
                    cache.remove(2, 19, pref.id)
                    cache.index(2).update()
                }
            }
        }
    )

    private fun varpAndVarbitToolBar() = arrayOf(
        button("Pack Varp") {
            disableWhen(varModel.selectedVarp.isNull)
            action {
                val cache = varModel.cache.get()
                val creds = account.activeCredentials.get()
                if (cache != null && creds != null) {
                    val varpDef = varpModel.commitVarp()
                    val json = gson.toJson(varpDef)
                    client.post<ByteArray>("tools/osrs/varps", StringBody(json), creds)
                        .catch {
                            alert(Alert.AlertType.ERROR, "Error Packing Varp", it.message)
                            emit(byteArrayOf())
                        }
                        .onEach {
                            cache.put(2, 16, varpDef.definitionId, it)
                            cache.index(2).update()
                            alert(
                                Alert.AlertType.INFORMATION,
                                "Packing Varp",
                                "Successfully packed varp definition."
                            )
                        }.launchIn(CoroutineScope(Dispatchers.JavaFx))
                }
            }
        },
        button("Add Varp") {
            action {
                val newVarp = VarpDefinition(varps.nextId)
                newVarp.type = 0
                varps.add(newVarp)
                varModel.variables[newVarp] = SimpleListProperty(FXCollections.observableArrayList())
                varModel.varps.add(newVarp)
            }
        },
        button("Remove Varp") {
            disableWhen(varModel.selectedVarp.isNull)
            action {
                val confirm = alert(
                    Alert.AlertType.CONFIRMATION,
                    "Delete Varp",
                    "Deleting this varp will delete all related varbits, are you sure you want to delete this varp?",
                    buttons = arrayOf(ButtonType.YES, ButtonType.NO)
                )
                if (confirm.result === ButtonType.YES) {
                    val secondConfirm = alert(
                        Alert.AlertType.CONFIRMATION,
                        "Varbit Deletion",
                        "Deleting this varp will delete ALL related varbits, continue?",
                        buttons = arrayOf(ButtonType.YES, ButtonType.NO)
                    )
                    if (secondConfirm.result === ButtonType.YES) {
                        deleteVarpAndVarbits(varModel.selectedVarp.get())
                    }
                }
            }
        },
        separator(),
        button("Pack Varbit") {
            disableWhen(varModel.selectedVarbit.isNull)
            action {
                val cache = varModel.cache.get()
                val creds = account.activeCredentials.get()
                if (cache != null && creds != null) {
                    val varbit = varbitModel.commitVarbit()
                    val varp = varps[varbit.index]
                    if (varp != null) {
                        var intersects = false
                        var ovarId = -1
                        val list = varModel.variables[varp]
                        if (list != null) {
                            if (list.isNotEmpty()) {
                                list.forEach {
                                    if (it.id != varbit.id && VariableTools.intersects(varp, it, varbit)) {
                                        intersects = true
                                        ovarId = it.id
                                        return@forEach
                                    }
                                }
                            }

                            if (!intersects) {
                                val json = gson.toJson(varbit)
                                client.post<ByteArray>("tools/osrs/varbits", StringBody(json), creds)
                                    .catch {
                                        alert(Alert.AlertType.ERROR, "Error Packing Varbit", it.message)
                                        emit(byteArrayOf())
                                    }
                                    .onEach {
                                        if (it.isNotEmpty()) {
                                            cache.put(2, 14, varbit.id, it)
                                            cache.index(2).update()
                                            alert(
                                                Alert.AlertType.INFORMATION,
                                                "Packing Varbit",
                                                "Successfully packed varbit"
                                            )
                                        }
                                    }.launchIn(CoroutineScope(Dispatchers.JavaFx))
                            } else {
                                alert(
                                    Alert.AlertType.ERROR,
                                    "Error Packing varbit",
                                    "Varbit ${varbit.id} value range overrides varbit $ovarId."
                                )
                            }
                        }
                    } else {
                        alert(Alert.AlertType.ERROR, "Linked Varp Error", "Varp ${varbit.index} does not exist.")
                    }
                }
            }
        },
        button("Add Varbit") {
            disableWhen(varModel.selectedVarp.isNull)
            action {
                val varp = varModel.selectedVarp.get()
                if (varp != null) {
                    val varbit = VarbitDefinition(varbits.nextId)
                    varbit.index = varp.definitionId
                    val list =
                        varModel.variables.getOrPut(varp) { SimpleListProperty(FXCollections.observableArrayList()) }
                    list.add(varbit)
                    varModel.varbits.add(varbit)
                    varbits.add(varbit)
                }
            }
        },
        button("Remove Varbit") {
            disableWhen(varModel.selectedVarbit.isNull)
            action {
                val varp = varModel.selectedVarp.get()
                val cache = varModel.cache.get()
                if (varp != null && cache != null) {
                    val confirm = alert(
                        Alert.AlertType.CONFIRMATION,
                        "Delete Varbit",
                        "Are you sure you want to delete this varbit?",
                        buttons = arrayOf(ButtonType.YES, ButtonType.NO)
                    )
                    if (confirm.result === ButtonType.YES) {
                        val varbit = varModel.selectedVarbit.get()
                        varModel.variables[varp]?.remove(varbit)
                        varModel.varbits.remove(varbit)
                        varbits.remove(varbit)
                        cache.remove(2, 14, varbit.id)
                        cache.index(2).update()
                    }
                }
            }
        }
    )

}