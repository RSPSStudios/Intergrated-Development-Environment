package com.javatar.plugin.definition.editor.ui.editor.sprites

import com.javatar.api.ui.utilities.datagrid
import com.javatar.osrs.definitions.impl.SpriteDefinition
import com.javatar.osrs.definitions.impl.SpriteGroupDefinition
import com.javatar.osrs.tools.SpriteSaver
import com.javatar.osrs.tools.SpriteTools
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.ui.editor.sprites.models.SpriteGridModel
import javafx.application.Platform
import javafx.beans.binding.Bindings
import javafx.collections.FXCollections
import javafx.embed.swing.SwingFXUtils
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.image.Image
import javafx.scene.image.PixelFormat
import javafx.scene.image.WritableImage
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import javax.imageio.ImageIO
import kotlin.io.path.pathString

class SpriteEditorFragment : Fragment("Sprite Editor") {

    val spriteModel: SpriteGridModel by inject()

    val sprites = OldSchoolDefinitionManager.sprites

    val encoder = SpriteSaver()

    init {
        spriteModel.cache.onChange {
            if (it != null) {
                loadSprites()
            }
        }
    }

    private fun loadSprites() {
        val cache = spriteModel.cache.get()
        if (cache != null) {
            val spriteIds = cache.index(8).archiveIds()
            spriteIds.forEach { id ->
                val spriteData = cache.data(8, id)
                if (spriteData != null) {
                    sprites.load(id, spriteData)
                }
            }
        }
        spriteModel.sprites.setAll(sprites.definitions.values.toList())
    }

    override val root = vbox {

        style = "-fx-base: #3f474f;"
        prefWidth = 900.0
        prefHeight = 600.0

        spacing = 20.0
        alignment = Pos.CENTER

        toolbar(
            button("Dump Sprites") {
                action {
                    val cache = spriteModel.cache.get()
                    if (cache != null) {
                        val dir = chooseDirectory("Choose dump directory") {
                            initialDirectory = File(System.getProperty("user.home"))
                        }
                        if (dir != null && dir.isDirectory) {
                            val loader = sprites.loader
                            val archiveIds = cache.index(8).archiveIds()
                            for (archiveId in archiveIds) {
                                val data = cache.data(8, archiveId)
                                if (data != null) {
                                    val group = loader.load(archiveId, data)
                                    if (group.sprites.size > 1) {
                                        val path = Path.of(dir.path, "group-$archiveId")
                                        if (!Files.exists(path)) {
                                            Files.createDirectories(path)
                                        }
                                        group.sprites.forEachIndexed { index, spriteDefinition ->
                                            SpriteTools.writeSpriteToFile(
                                                Path.of(path.pathString, "frame-$index.png").toFile(), spriteDefinition
                                            )
                                        }
                                    } else if (group.sprites.size == 1) {
                                        val sprite = group.sprites[0]
                                        SpriteTools.writeSpriteToFile(
                                            Path.of(dir.path, "sprite-${group.groupId}.png").toFile(), sprite
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            },
            button("Import Sprites") {
                action {
                    val files = chooseFile(
                        "Choose PNG Images",
                        arrayOf(FileChooser.ExtensionFilter("PNG Images (*.png)", "*.png"))
                    ) {
                        initialDirectory = File(System.getProperty("user.home"))
                    }
                    val cache = spriteModel.cache.get()
                    if (files.isNotEmpty()) {
                        files.forEach {
                            val nextGroupId = sprites.nextId
                            val image = Image(it.toURI().toString())
                            val group = SpriteGroupDefinition()
                            group.groupId = nextGroupId
                            val g = SpriteTools.fromFXImage(group, image)
                            sprites.add(g)
                            spriteModel.sprites.add(g)
                            if(cache != null) {
                                val bytes = encoder.save(g)
                                cache.put(8, g.groupId, bytes)
                                cache.index(8).update()
                            }
                        }
                    }
                }
            },
            button("Delete Sprite") {
                val cache = spriteModel.cache.get()
                if(cache != null) {
                    val group = spriteModel.selectedSprite.get()
                    if(group != null) {
                        spriteModel.sprites.remove(group)
                        sprites.remove(group)
                        cache.remove(8, group.groupId)
                        cache.index(8).update()
                    }
                }
            })

        hbox {
            spacing = 20.0
            fitToParentSize()
            listview(spriteModel.sprites) {
                minWidth = 200.0
                spriteModel.selectedSprite.bind(selectionModel.selectedItemProperty())
                cellFormat {
                    text = "Sprite ${item.groupId} - ${item.width} x ${item.height}"
                }
            }
            datagrid<SpriteDefinition> {
                prefWidth = 1000.0
                prefHeight = 900.0
                itemsProperty.bind(Bindings.createObjectBinding({
                    spriteModel.selectedSprite.get()?.sprites?.toList()?.toObservable()
                        ?: FXCollections.emptyObservableList()
                }, spriteModel.selectedSprite))
                cellFormat {
                    style = "-fx-background: transparent;"
                    graphic = vbox {
                        spacing = 10.0
                        alignment = Pos.CENTER
                        val sprite = it
                        if (sprite.width > 0 && sprite.height > 0 && sprite.pixels.isNotEmpty()) {
                            val image = WritableImage(sprite.width, sprite.height)
                            this@cellFormat.prefWidth = sprite.width.toDouble()
                            this@cellFormat.prefHeight = sprite.height.toDouble()
                            image.pixelWriter.setPixels(
                                0,
                                0,
                                sprite.width,
                                sprite.height,
                                PixelFormat.getIntArgbPreInstance(),
                                sprite.pixels,
                                0,
                                sprite.width
                            )
                            imageview(image)
                            contextmenu {
                                item("Dump Sprite").action {
                                    val file = chooseFile(
                                        "Choose Sprite Dump Location",
                                        mode = FileChooserMode.Save,
                                        filters = arrayOf(FileChooser.ExtensionFilter("PNG File (*.png)", "*.png"))
                                    ) {
                                        initialDirectory = File(System.getProperty("user.home"))
                                    }
                                    if (file.isNotEmpty() && file.size == 1) {
                                        val imageFile = file[0]
                                        val bufImage = SwingFXUtils.fromFXImage(image, null)
                                        ImageIO.write(bufImage, "png", imageFile)
                                    }
                                }
                                separator()
                                item("Add Sprite To Group").action {
                                    val group = spriteModel.selectedSprite.get()
                                    val cache = spriteModel.cache.get()
                                    if (group != null && cache != null) {
                                        val file = chooseFile(
                                            "Choose PNG Image",
                                            arrayOf(FileChooser.ExtensionFilter("PNG file (*.png)", "*.png"))
                                        )
                                        val spriteEncoder = SpriteSaver()
                                        file.forEach {
                                            val pngImage = SwingFXUtils.toFXImage(ImageIO.read(it), null)
                                            val mgroup = SpriteTools.fromFXImage(group, pngImage)
                                            cache.put(8, mgroup.groupId, spriteEncoder.save(mgroup))
                                            cache.index(8).update()
                                            alert(
                                                Alert.AlertType.INFORMATION,
                                                "Packing Sprite ${group.groupId}",
                                                "Packing successful."
                                            )
                                        }
                                    }
                                }
                            }
                        } else {
                            label("No Display.")
                        }
                    }
                }
            }
        }
    }

}