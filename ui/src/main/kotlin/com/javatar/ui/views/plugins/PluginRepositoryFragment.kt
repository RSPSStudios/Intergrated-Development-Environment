package com.javatar.ui.views.plugins

import com.javatar.api.nexus.repository.NexusRepository
import com.javatar.ui.models.PluginRepositoryModel
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*

class PluginRepositoryFragment : Fragment("Plugin Repositories") {

    val pluginRepository: PluginRepositoryModel by di()
    private val selected = SimpleObjectProperty<NexusRepository>(this, "selected")

    override val root = hbox {
        listview(pluginRepository.pluginRepositories) {
            selected.bind(selectionModel.selectedItemProperty())
            cellFormat {
                text = it.url
                tooltip(it.url)
            }
        }
        form {
            fieldset("Management") {
                field {
                    button("Add Repository") {

                    }
                }
                field {
                    button("Remove Repository") {
                        disableWhen(selected.isNull)
                    }
                }
            }
            fieldset("Credentials") {
                field("Username") {
                    textfield()
                }
                field("Password") {
                    passwordfield()
                }
            }
        }
    }

}