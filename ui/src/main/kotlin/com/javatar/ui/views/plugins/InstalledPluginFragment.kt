package com.javatar.ui.views.plugins

import com.javatar.api.ui.models.EventLogModel
import com.javatar.ui.data.PluginInformation
import com.javatar.ui.models.NexusComponentModel
import com.javatar.ui.models.PluginRepositoryModel
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*

class InstalledPluginFragment : Fragment("Installed Plugins") {

    val events: EventLogModel by di()

    val pluginRepository: PluginRepositoryModel by di()
    val nexus: NexusComponentModel by inject()

    private val selectedPlugin = SimpleObjectProperty<PluginInformation>(this, "selected_plugin")

    override val root = hbox {
        listview<PluginInformation> {
            selectedPlugin.bind(selectionModel.selectedItemProperty())
            itemsProperty().bind(Bindings.createObjectBinding({
                pluginRepository.plugins.values.toList().toObservable()
            }, pluginRepository.plugins))
            cellFormat {
                text = "${it.pluginName} - ${it.pluginVersion}"
            }
        }
        vbox {
            dynamicContent(selectedPlugin) {
                if(it != null) {
                    form {
                        fieldset("Plugin Details") {
                            field("Name") {
                                label(it.pluginName)
                            }
                            field("Author") {
                                label(it.pluginAuthor)
                            }
                            field("Version") {
                                label(it.pluginVersion)
                            }
                            field("Description") {
                                label(it.pluginDescriptor)
                            }
                            field("sha1") {
                                textfield(it.sha1)
                                tooltip(it.sha1)
                            }
                            field("sha256") {
                                textfield(it.sha256)
                                tooltip(it.sha256)
                            }
                            field("sha512") {
                                textfield(it.sha512)
                                tooltip(it.sha512)
                            }
                            field(it.md5) {
                                textfield(it.md5)
                                tooltip(it.md5)
                            }
                        }
                        separator()
                        fieldset("Actions") {
                            disableWhen(selectedPlugin.isNull)
                            val plugin = selectedPlugin.get()
                            if(plugin != null) {
                                val nexusComp = nexus.availablePlugins.find { c -> plugin.pluginName == c.name }
                                if(nexusComp != null && nexusComp.assets.isNotEmpty()) {
                                    val asset = nexusComp.assets[0]
                                    if(asset.checksum["sha512"] != plugin.sha512) {
                                        button("Update").action {
                                            val link = asset.downloadUrl
                                            println(link)
                                        }
                                    }
                                }
                            }
                            button("Delete") {
                                disableWhen(selectedPlugin.isNull)
                                action {
                                    println("Deleted!")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}