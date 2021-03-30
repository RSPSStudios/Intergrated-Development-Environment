package com.javatar.plugin.definition.editor.ui

import com.javatar.api.http.Client
import com.javatar.api.ui.models.AccountModel
import com.javatar.plugin.definition.editor.ui.models.GenericDefinitionEditingModel
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.stage.FileChooser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.javafx.JavaFx
import tornadofx.*
import java.io.File

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

class GenericDefinitionTool : Fragment() {

    val editorModel: GenericDefinitionEditingModel by inject()

    override val root = vbox {
        spacing = 10.0
        hbox {
            alignment = Pos.CENTER
            spacing = 10.0
            button("Pack").action {
                editorModel.fileType.get().save(editorModel.json.get(), editorModel.fileNode.get(), editorModel.root.get())
            }
            button("Export").action {
                val files = chooseFile("Export file", mode = FileChooserMode.Save, filters = arrayOf(FileChooser.ExtensionFilter("txt"))) {
                    initialDirectory = if (editorModel.last_export.get() != null) {
                        File(editorModel.last_export.get())
                    } else File(System.getProperty("user.home"))
                }
                if(files.isNotEmpty()) {
                    val file = files[0]
                    file.writeText(editorModel.json.get())
                    editorModel.last_export.set(file.absolutePath)
                    editorModel.commit()
                }
            }
        }
        textarea(editorModel.json) {
            prefHeight = 650.0
        }
    }

}
