package com.javatar.ui.models

import com.google.common.hash.Hashing
import com.google.common.io.Files.asByteSource
import com.javatar.api.nexus.NexusCredentials
import com.javatar.api.nexus.repository.NexusRepository
import com.javatar.ui.data.PluginInformation
import javafx.application.HostServices
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleMapProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import org.pf4j.PluginManager
import tornadofx.ViewModel
import java.io.File

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

class PluginRepositoryModel : ViewModel() {

    val manager: PluginManager by di()

    val plugins = bind { SimpleMapProperty<String, PluginInformation>(FXCollections.observableHashMap()) }

    val services = SimpleObjectProperty<HostServices>(this, "host_services")

    val pluginRepositories = SimpleListProperty<NexusRepository>(this, "plugin_repositories", FXCollections.observableArrayList())

    init {
        pluginRepositories.add(
            NexusRepository(
                "http://legionkt.com:8080/service/rest/v1/components?repository=rsps-studio-plugins",
                NexusCredentials.ANONYMOUS
        ))
    }

    fun calculateHashes() {
        manager.plugins.forEach { wrapper ->
            val source = asByteSource(File("${wrapper.pluginPath}.zip"))
            val sha512 = source.hash(Hashing.sha512()).toString()
            plugins[wrapper.pluginId]?.sha512 = sha512
        }
    }

}
