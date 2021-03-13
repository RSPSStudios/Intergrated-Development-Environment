package com.javatar.ui.models

import com.javatar.ui.data.PluginInformation
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import org.pf4j.JarPluginManager
import tornadofx.ViewModel

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

class PluginRepositoryModel : ViewModel() {

    val manager: JarPluginManager by di()

    val plugins = bind { SimpleListProperty(FXCollections.observableArrayList<PluginInformation>()) }

}
