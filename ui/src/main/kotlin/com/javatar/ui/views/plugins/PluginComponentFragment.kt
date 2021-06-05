package com.javatar.ui.views.plugins

import com.javatar.api.nexus.NexusClient
import com.javatar.ui.models.NexusComponentModel
import com.javatar.ui.models.PluginRepositoryModel
import tornadofx.*

class PluginComponentFragment : Fragment("Available Plugins") {

    val model: NexusComponentModel by inject()
    val repositoryModel: PluginRepositoryModel by di()

    override val root = hbox {
        listview(model.availablePlugins) {
            model.selectedAvailablePlugin.bind(selectionModel.selectedItemProperty())
            cellFormat {
                text = it.name.replace("plugins/", "")
                    .replace(".zip", "")
            }
        }
        form {
            hiddenWhen(model.selectedAvailablePlugin.isNull)
            fieldset("Information") {
                field("ID") {
                    label(model.id) {
                        tooltip {
                            textProperty().bind(model.id)
                        }
                    }
                }
                field("Name") {
                    label(model.name)
                }
                field("Version") {
                    label(model.version)
                }
                field("Repository") {
                    label(model.repository)
                }
                field("Format") {
                    label(model.format)
                }
                field("Group") {
                    label(model.group)
                }
            }
            fieldset("Assets") {
                separator()
                vbox {
                    prefWidthProperty().bind(this@fieldset.wrapWidthProperty)
                    dynamicContent(model.assets) {
                        model.assets.forEach {
                            field("Asset ID") {
                                label(it.id) {
                                    tooltip(it.id)
                                }
                            }
                            field("Content Type") {
                                label(it.contentType)
                            }
                            field("Repository") {
                                label(it.repository)
                            }
                            field("Repository Format") {
                                label(it.format)
                            }
                            field("Path") {
                                label(it.path) {
                                    tooltip(it.path)
                                }
                            }
                            field("Last Modified") {
                                label(it.lastModified)
                            }
                            field("Download URL") {
                                hyperlink(it.downloadUrl) {
                                    tooltip(it.downloadUrl)
                                }.action {
                                    repositoryModel.services.get()?.showDocument(it.downloadUrl)
                                }
                            }
                            it.checksum.entries.forEach { entry ->
                                field(entry.key) {
                                    label(entry.value)
                                    tooltip(entry.value)
                                }
                            }
                            separator()
                        }
                    }
                }
            }
        }
    }

}