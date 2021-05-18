package com.javatar.plugin.definition.editor.ui.rsmenu

import com.javatar.plugin.definition.editor.ui.TextUtils
import com.javatar.plugin.definition.editor.ui.rsmenu.model.ActionEditorModel
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleStringProperty
import javafx.event.EventType
import javafx.geometry.Pos
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.input.ClipboardContent
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.input.TransferMode
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.util.Callback
import tornadofx.*

class ListCellFactory(val targetName: SimpleStringProperty, val rsBoldFont: Font, val model: ActionEditorModel) : Callback<ListView<String>, ListCell<String>> {

    override fun call(param: ListView<String>): ListCell<String> {
        return Cell()
    }

    inner class Cell : ListCell<String>() {

        private val editorField = textfield {
            alignment = Pos.CENTER
            prefWidthProperty().bind(Bindings.createDoubleBinding({
                TextUtils.computeTextWidth(rsBoldFont, text)
            }, textProperty()))
            styleClass.remove("text-input")
            font = rsBoldFont
            textFill = Color.WHITE
            style {
                textFill = Color.WHITE
            }
            setOnAction {
                commitEdit(text)
            }
            addEventFilter(KeyEvent.KEY_RELEASED) { e ->
                if (e.code === KeyCode.ESCAPE) {
                    cancelEdit()
                }
            }
        }

        init {
            style = null
            styleClass.clear()

            style {
                backgroundColor = MultiValue(arrayOf(c("#5D5447")))
            }
            selectedProperty().onChange {
                if(it) {
                    style {
                        backgroundColor = MultiValue(arrayOf(c("#5D5447")))
                        borderColor += box(Color.RED)
                    }
                } else {
                    style {
                        backgroundColor = MultiValue(arrayOf(c("#5D5447")))
                    }
                }
            }

            setOnDragDetected {
                if(item == null)
                    return@setOnDragDetected
                val dragBoard = startDragAndDrop(TransferMode.MOVE)
                val clipContent = ClipboardContent()
                clipContent.putString(item)
                dragBoard.dragView = snapshot(null, null)
                dragBoard.setContent(clipContent)
                it.consume()
            }

            setOnDragOver {
                if (it.gestureSource != this && it.dragboard.hasString()) {
                    it.acceptTransferModes(TransferMode.MOVE)
                }
                it.consume()
            }

            setOnDragEntered {
                if (it.gestureSource != this && it.dragboard.hasString()) {
                    opacity = 0.3
                }
            }

            setOnDragExited {
                if (it.gestureSource != this && it.dragboard.hasString()) {
                    opacity = 1.0
                }
            }

            setOnDragDropped {
                if(item == null)
                    return@setOnDragDropped
                val db = it.dragboard
                var success = false
                if(db.hasString()) {
                    val items = listView.items
                    val draggedIndex = items.indexOf(db.string)
                    val thisIndex = index
                    items[draggedIndex] = item
                    items[thisIndex] = db.string
                    listView.items.setAll(mutableListOf(*items.toTypedArray()))
                    success = true
                }
                it.isDropCompleted = success
                it.consume()
            }

        }

        override fun startEdit() {
            super.startEdit()
            editorField.text = item
            text = null
            graphic = buildGraphicForEditing()
        }

        override fun cancelEdit() {
            super.cancelEdit()
            text = null
            graphic = buildGraphicForNoneEditing(item)
        }

        override fun commitEdit(newValue: String?) {
            super.commitEdit(newValue)
            if(newValue != null && newValue.isNotEmpty()) {
                text = null
                graphic = buildGraphicForNoneEditing(newValue)
            }
        }


        override fun updateItem(item: String?, empty: Boolean) {
            super.updateItem(item, empty)
            if(empty || item == null) {
                text = null
                graphic = null
            } else if(isEditing) {
                text = null
                editorField.text = item
                graphic = buildGraphicForEditing()
            } else {
                text = null
                graphic = buildGraphicForNoneEditing(item)
            }
        }

        private fun buildGraphicForEditing() = hbox {
            spacing = 5.0
            alignment = Pos.CENTER_LEFT
            add(editorField)
            if (item != "Cancel") {
                text(targetName.get()) {
                    font = rsBoldFont
                    fill = Color.CYAN
                    translateY = 1.0
                }
            }
        }

        private fun buildGraphicForNoneEditing(item: String) = hbox {
            spacing = 5.0
            alignment = Pos.CENTER_LEFT
            text(item) {
                font = rsBoldFont
                fill = Color.WHITE
            }
            if (item != "Cancel") {
                text(targetName.get()) {
                    font = rsBoldFont
                    fill = Color.CYAN
                }
            }
        }
    }
}