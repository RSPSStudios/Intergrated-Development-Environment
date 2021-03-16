package com.javatar.ui.views

import javafx.geometry.Pos
import tornadofx.*
import java.io.File

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 16 2021
 */

class PluginPreferencesView : Fragment() {

    val pluginPath = app.config.string("PLUGIN_DIR")

    override val root = vbox {
        alignment = Pos.CENTER
        spacing = 10.0

        label("Plugin Path")

        button("Set Location") {
            textfield(pluginPath) {
                isEditable = false
            }
        }.action {
            val dirChooser = chooseDirectory("Choose Plugin Directory") {
                initialDirectory = File(System.getProperty("user.home"))
            }
            if (dirChooser != null && dirChooser.exists() && dirChooser.isDirectory) {
                app.config["PLUGIN_DIR"] = dirChooser.absolutePath
                app.config.save()
            }
        }


    }

}
