package com.javatar.ui.views

import com.javatar.api.ui.PreferencesExtension
import com.javatar.ui.models.PluginRepositoryModel
import com.javatar.ui.models.PreferenceModel
import com.javatar.ui.views.cell.PreferenceTreeCell
import javafx.geometry.Pos
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import tornadofx.Fragment
import tornadofx.dynamicContent

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 16 2021
 */

class PreferencesView : Fragment() {

    override val root: BorderPane by fxml("preferences.fxml")

    val preferencesList: TreeView<PreferenceModel> by fxid()

    val pluginRepo: PluginRepositoryModel by di()

    val preferenceView: VBox by fxid()

    init {

        val root = TreeItem(PreferenceModel("Preferences", GeneralPreferencesView()))
        with(root) {
            preferencesList.root = this
            isExpanded = true
        }

        with(preferencesList) {
            setCellFactory { PreferenceTreeCell() }
        }

        with(preferenceView) {
            alignment = Pos.CENTER
            spacing = 10.0
            dynamicContent(preferencesList.selectionModel.selectedItemProperty()) {
                if (it != null) {
                    add(it.value.node.get())
                }
            }
        }

        with(root.children) {
            add(TreeItem(PreferenceModel("Plugins", PluginPreferencesView())).apply {
                pluginRepo.manager.getExtensions(PreferencesExtension::class.java)
                    .forEach {
                        this.children.add(TreeItem(PreferenceModel(it.name(), it.createPreferencesUI())))
                    }
            })
        }
    }

}
