package com.javatar.plugin.definition.editor.ui.rsmenu

import com.javatar.plugin.definition.editor.OsrsDefinitionEditor
import com.javatar.plugin.definition.editor.ui.rsmenu.model.ActionEditorModel
import javafx.beans.binding.Bindings
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import tornadofx.*
import java.io.File

class ActionEditor(val model: ActionEditorModel) : VBox() {

    private val backgroundColor = c("#5D5447")
    private val resourceURL = "${OsrsDefinitionEditor.properties["resourcePath"]}/osrs-fonts/RuneScape-Bold-12.ttf"
    private val url = File(resourceURL)
    private val rsBoldFont = Font.loadFont(url.inputStream(), 16.0)

    init {
        val paneX = 0.0
        val paneY = 0.0
        style {
            borderColor += box(Color.BLACK)
            borderWidth += box(Dimension(0.5, Dimension.LinearUnits.px))
            backgroundColor += Color.BLACK
        }
        val t = text("Choose Option") {
            fill = backgroundColor
            font = rsBoldFont
            x = (paneX + 3)
            y = (paneY + 14)
        }
        listview(model.actions) {
            model.selected.bind(selectionModel.selectedItemProperty())
            isEditable = true
            prefHeightProperty().bind(Bindings.createDoubleBinding({
                (model.actions.size * 18).toDouble()
            }, model.actions))
            styleClass.clear()
            cellFactory = ListCellFactory(model.targetName, rsBoldFont, model)
            layoutX + paneX + 3
            layoutY =
                t.layoutY + t.layoutBounds.height + 3
        }
    }


}