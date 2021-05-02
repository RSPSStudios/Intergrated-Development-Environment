package com.javatar.plugin.definition.editor.ui.editor.items

import com.javatar.api.ui.toFXColor
import com.javatar.api.ui.toInt
import com.javatar.osrs.definitions.sprites.ItemSpriteFactory
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.managers.ItemProvider
import com.javatar.plugin.definition.editor.managers.ModelProvider
import com.javatar.plugin.definition.editor.managers.SpriteProvider
import com.javatar.plugin.definition.editor.managers.TextureProvider
import com.javatar.plugin.definition.editor.ui.editor.items.model.ItemDefinitionModel
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.embed.swing.SwingFXUtils
import javafx.geometry.Pos
import javafx.geometry.Rectangle2D
import javafx.scene.image.Image
import javafx.scene.image.PixelBuffer
import javafx.scene.image.PixelFormat
import javafx.scene.image.WritableImage
import tornadofx.*
import java.nio.IntBuffer

class ItemInventoryFragment : Fragment() {

    val def: ItemDefinitionModel by inject()

    private val pixelBuffer = PixelBuffer(36, 32, IntBuffer.allocate(36 * 32), PixelFormat.getIntArgbPreInstance())
    private val itemSprite = WritableImage(pixelBuffer)

    private val factory: ItemSpriteFactory by lazy {
        val cache = def.cacheProperty.get()
        ItemSpriteFactory(
            ItemProvider(cache),
            ModelProvider(cache),
            SpriteProvider(cache),
            TextureProvider(cache),
            pixelBuffer
        )
    }

    val manager = OldSchoolDefinitionManager.items

    init {

        def.xan2d.onChange { onItemChange() }
        def.yan2d.onChange { onItemChange() }
        def.zan2d.onChange { onItemChange() }
        def.xOffset2d.onChange { onItemChange() }
        def.yOffset2d.onChange { onItemChange() }
        def.resizeX.onChange { onItemChange() }
        def.resizeY.onChange { onItemChange() }
        def.resizeZ.onChange { onItemChange() }
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
        imageview(itemSprite)
        scrollpane {
            form {
                maxWidth = 510.0
                fieldset("2D Angles") {
                    field("X Angle") {
                        spinner(property = def.xan2d, editable = true, min = 0, max = Int.MAX_VALUE)
                    }
                    field("Y Angle") {
                        spinner(property = def.yan2d, editable = true, min = 0, max = Int.MAX_VALUE)
                    }
                    field("Z Angle") {
                        spinner(property = def.zan2d, editable = true, min = 0, max = Int.MAX_VALUE)
                    }
                    field("X Offset") {
                        spinner(property = def.xOffset2d, editable = true, min = 0, max = Int.MAX_VALUE)
                    }
                    field("Y Offset") {
                        spinner(property = def.yOffset2d, editable = true, min = 0, max = Int.MAX_VALUE)
                    }
                }
                fieldset("Resizing") {
                    field("Resize X Axis") {
                        spinner(property = def.resizeX, editable = true, min = 0, max = Int.MAX_VALUE)
                    }
                    field("Resize Y Axis") {
                        spinner(property = def.resizeY, editable = true, min = 0, max = Int.MAX_VALUE)
                    }
                    field("Resize Z Axis") {
                        spinner(property = def.resizeZ, editable = true, min = 0, max = Int.MAX_VALUE)
                    }
                }
                fieldset("Colors") {
                    field {
                        listview(def.colorFind) {
                            cellFormat {
                                graphic = colorpicker(item.toFXColor()) {
                                    valueProperty().onChange {
                                        if (it != null) {
                                            def.colorFind[def.colorFind.indexOf(item)] = it.toInt()
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
        val item = def.createItem()
        manager.add(item)
        pixelBuffer.buffer.clear()
        factory.createSpriteToPixelBuffer(
            def.id.get(),
            1,
            1,
            3153952,
            def.notedTemplate.get() != -1
        )
        pixelBuffer.updateBuffer {
            Rectangle2D(
                def.xOffset2d.get().toDouble(),
                def.yOffset2d.get().toDouble(),
                36.0,
                32.0
            )
        }
    }

}