package com.javatar.plugin.definition.editor.ui.editor.items

import com.displee.cache.CacheLibrary
import com.javatar.api.ui.toFXColor
import com.javatar.api.ui.toInt
import com.javatar.definition.DefinitionProvider
import com.javatar.osrs.definitions.impl.ItemDefinition
import com.javatar.osrs.definitions.sprites.ItemSpriteFactory
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.managers.ItemProvider
import com.javatar.plugin.definition.editor.managers.ModelProvider
import com.javatar.plugin.definition.editor.managers.SpriteProvider
import com.javatar.plugin.definition.editor.managers.TextureProvider
import com.javatar.plugin.definition.editor.ui.editor.items.model.ItemDefinitionModel
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleObjectProperty
import javafx.embed.swing.SwingFXUtils
import javafx.geometry.Pos
import javafx.scene.image.Image
import tornadofx.*

class ItemInventoryFragment : Fragment() {

    val def: ItemDefinitionModel by inject()

    private val factory = ItemSpriteFactory()

    private val spriteProperty = SimpleObjectProperty<Image>()

    val manager = OldSchoolDefinitionManager.items

    init {
        spriteProperty.bind(Bindings.createObjectBinding(
            {
                val cache = def.cacheProperty.get()
                val item = def.createItem()
                manager.add(item)
                if (cache != null) SwingFXUtils.toFXImage(
                    factory.createSprite(
                        ItemProvider(cache),
                        ModelProvider(cache),
                        SpriteProvider(cache),
                        TextureProvider(cache),
                        def.id.get(),
                        1,
                        1,
                        3153952,
                        def.notedTemplate.get() != -1
                    ), null
                ) else null
            },
            def.xan2d,
            def.yan2d,
            def.zan2d,
            def.xOffset2d,
            def.yOffset2d,
            def.resizeX,
            def.resizeY,
            def.resizeZ,
            def.colorFind,
            def.colorReplace,
            def.textureFind,
            def.textureReplace
        ))
    }

    override val root = vbox {
        spacing = 10.0
        alignment = Pos.CENTER
        prefWidth = 514.0
        maxHeight = 455.0
        imageview(spriteProperty)
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
                                colorpicker(item.toFXColor()) {
                                    valueProperty().onChange {
                                        if(it != null) {
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
}