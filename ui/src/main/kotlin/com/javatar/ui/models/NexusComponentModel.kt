package com.javatar.ui.models

import com.javatar.api.nexus.api.data.NexusAsset
import com.javatar.api.nexus.api.data.NexusComponent
import com.javatar.ui.data.PluginInformation
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel
import tornadofx.onChange

class NexusComponentModel : ViewModel() {

    val availablePlugins = SimpleListProperty<NexusComponent>(this, "available_plugins", FXCollections.observableArrayList())
    val selectedAvailablePlugin = SimpleObjectProperty<NexusComponent>(this, "selected_available_plugin")

    val id = bind { SimpleStringProperty(this, "id", "Unknown") }
    val repository = bind { SimpleStringProperty(this, "repository", "Unknown") }
    val format = bind { SimpleStringProperty(this, "format", "Unknown") }
    val group = bind { SimpleStringProperty(this, "group", "Unknown") }
    val name = bind { SimpleStringProperty(this, "name", "Unknown") }
    val version = bind { SimpleStringProperty(this, "version", "Unknown") }
    val assets = bind { SimpleListProperty<NexusAsset>(this, "assets", FXCollections.observableArrayList()) }

    val downloadURL = bind { SimpleStringProperty(this, "download_url", "") }

    fun update(comp: NexusComponent) {
        id.set(comp.id)
        repository.set(comp.repository)
        format.set(comp.format)
        group.set(comp.group)
        name.set(comp.name)
        version.set(comp.version)
        assets.setAll(comp.assets)
    }

    init {
        selectedAvailablePlugin.onChange {
            if(it != null) {
                update(it)
            }
        }
    }
}