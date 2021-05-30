package com.javatar.ui.models

import com.javatar.ui.data.PluginInformation
import javafx.application.HostServices
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleMapProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import org.koin.core.component.KoinComponent
import org.pf4j.PluginManager
import org.pf4j.PluginWrapper
import tornadofx.ViewModel

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

class PluginRepositoryModel : ViewModel() {

    val manager: PluginManager by di()

    val plugins = bind { SimpleMapProperty<String, PluginInformation>(FXCollections.observableHashMap()) }

    val services = SimpleObjectProperty<HostServices>(this, "host_services")

    val pluginRepositories = SimpleListProperty<String>(this, "plugin_repositories", FXCollections.observableArrayList())

}
