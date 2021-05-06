package com.javatar.plugin.definition.editor.ui.editor.items

import com.javatar.api.ui.clearPixels
import com.javatar.api.ui.toColor
import com.javatar.api.ui.toRS2
import com.javatar.api.ui.utilities.datagrid
import com.javatar.osrs.definitions.loaders.ModelLoader
import com.javatar.osrs.tools.ItemColorGeneration
import com.javatar.plugin.definition.editor.ui.editor.items.model.ItemDefinitionModel
import javafx.collections.ListChangeListener
import javafx.geometry.Pos
import javafx.geometry.Rectangle2D
import tornadofx.*

class ItemInventoryFragment : Fragment() {

    val def: ItemDefinitionModel by inject()

    init {
        def.xan2d.onChange { onItemChange() }
        def.yan2d.onChange { onItemChange() }
        def.zan2d.onChange { onItemChange() }
        def.xOffset2d.onChange { onItemChange() }
        def.yOffset2d.onChange { onItemChange() }
        def.resizeX.onChange { onItemChange() }
        def.resizeY.onChange { onItemChange() }
        def.resizeZ.onChange { onItemChange() }
        def.zoom2d.onChange { onItemChange() }
        def.colorFind.onChange { change: ListChangeListener.Change<out Int> ->
            onItemChange()
        }
        def.colorReplace.onChange { change: ListChangeListener.Change<out Int> ->
            onItemChange()
        }
        def.textureFind.onChange { change: ListChangeListener.Change<out Int> ->
            onItemChange()
        }
        def.textureReplace.onChange { change: ListChangeListener.Change<out Int> ->
            onItemChange()
        }
    }

    override val root = vbox {
        spacing = 10.0
        alignment = Pos.CENTER
        prefWidth = 514.0
        maxHeight = 455.0
        imageview {
            imageProperty().bind(def.itemSprite)
        }
        scrollpane {
            form {
                maxWidth = 510.0
                fieldset("2D Angles") {
                    field("X Angle") {
                        spinner(property = def.xan2d, editable = true, min = 0, max = Int.MAX_VALUE)
                    }
                    field("Y Angle") {
                        spinner(property = def.yan2d, editable = true, min = 0, max = Int.MAX_VALUE) {
                            this.editor.stripNonNumeric()
                        }
                    }
                    field("Z Angle") {
                        spinner(property = def.zan2d, editable = true, min = 0, max = Int.MAX_VALUE) {
                            this.editor.stripNonNumeric()
                        }
                    }
                    field("X Offset") {
                        spinner(property = def.xOffset2d, editable = true, min = 0, max = Int.MAX_VALUE) {
                            this.editor.stripNonNumeric()
                        }
                    }
                    field("Y Offset") {
                        spinner(property = def.yOffset2d, editable = true, min = 0, max = Int.MAX_VALUE) {
                            this.editor.stripNonNumeric()
                        }
                    }
                }
                fieldset("Resizing") {
                    field("Resize X Axis") {
                        spinner(property = def.resizeX, editable = true, min = 0, max = Int.MAX_VALUE) {
                            this.editor.stripNonNumeric()
                        }
                    }
                    field("Resize Y Axis") {
                        spinner(property = def.resizeY, editable = true, min = 0, max = Int.MAX_VALUE) {
                            this.editor.stripNonNumeric()
                        }
                    }
                    field("Resize Z Axis") {
                        spinner(property = def.resizeZ, editable = true, min = 0, max = Int.MAX_VALUE) {
                            this.editor.stripNonNumeric()
                        }
                    }
                }

                fieldset("Other") {
                    field("Inventory Model") {
                        spinner(property = def.inventoryModel, editable = true, min = 0, max = Int.MAX_VALUE) {
                            this.editor.stripNonNumeric()
                        }
                    }
                    field("Zoom") {
                        spinner(property = def.zoom2d, editable = true, min = 0, max = Int.MAX_VALUE) {
                            this.editor.stripNonNumeric()
                        }
                    }
                }

                fieldset("Colors") {
                    field {
                        button("Generate Colors").action {
                            val cache = def.cacheProperty.get()
                            if (cache != null) {
                                val loader = ModelLoader()
                                val data = cache.data(7, def.inventoryModel.get())
                                val mdef = loader.load(def.inventoryModel.get(), data)
                                val colors = ItemColorGeneration.generateOriginalColors(mdef)
                                def.colorFind.setAll(colors.toList().map { it.toInt() })
                                def.colorReplace.setAll(colors.toList().map { it.toInt() })
                            }
                        }
                    }
                    field {
                        datagrid(def.colorReplace) {
                            itemsProperty.bind(def.colorReplace)
                            maxCellsInRow = 3
                            cellFormat { v ->
                                graphic = colorpicker(v.toColor()) {
                                    setOnAction {
                                        if (value != null) {
                                            val index = def.colorReplace.indexOf(v)
                                            if (index != -1) {
                                                def.colorReplace[index] = value.toRS2()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onItemChange() {
        def.pixelBuffer.clearPixels()
        def.pixelBuffer.buffer.clear()
        val factory = def.itemSpriteFactory.get()
        if (factory != null) {
            val item = def.createItem()

            if (item.colorFind.size != item.colorReplace.size)
                return

            factory.writeSpriteToPixelBuffer(
                item,
                1,
                1,
                3153952,
                def.notedTemplate.get() != -1,
                def.pixelBuffer
            )

            def.pixelBuffer.updateBuffer {
                Rectangle2D(
                    0.0,
                    0.0,
                    36.0,
                    32.0
                )
            }
        }
    }

}