package com.javatar.plugin.definition.editor.ui

import com.javatar.plugin.definition.editor.ui.models.MapPackerModel
import javafx.geometry.Pos
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File

class MapPacker : Fragment("Old School Map Packer") {

    val mapPackerModel: MapPackerModel by inject()

    override val root = form {
        style = "-fx-base: #3f474f;"
        alignment = Pos.CENTER
        spacing = 20.0
        fieldset("Map Files") {
            field {
                button("Select Objects (Object Definitions)") {
                    textfield(mapPackerModel.landscapeFile) {
                        isEditable = false
                    }
                }.action {
                    val files = chooseFile(
                        "Choose Objects (Object Definitions)",
                        arrayOf(FileChooser.ExtensionFilter("dat")),
                    )
                    if(files.isNotEmpty()) {
                        val file = files[0]
                        mapPackerModel.landscapeFile.set(file.absolutePath)
                    }
                }
            }
            field {
                button("Select Floors (Underlays & Overlays)") {
                    textfield(mapPackerModel.floorFile) {
                        isEditable = false
                    }
                }.action {
                    val files = chooseFile(
                        "Choose Floors (Underlays & Overlays)",
                        arrayOf(FileChooser.ExtensionFilter("dat")),
                    )
                    if(files.isNotEmpty()) {
                        val file = files[0]
                        mapPackerModel.floorFile.set(file.absolutePath)
                    }
                }
            }
            field("Region ID") {
                textfield(mapPackerModel.regionId) { stripNonInteger() }
            }
        }
        fieldset("Pack Map Data") {
            field {
                button("Pack Map") {
                    enableWhen(mapPackerModel.floorFile.isNotNull.and(mapPackerModel.landscapeFile.isNotEmpty).and(mapPackerModel.regionId.isNotNull))
                }.action {
                    val root = mapPackerModel.root.get()
                    if(root != null) {
                        val maps = root.cache.index(5)
                        val regionId = mapPackerModel.regionId.get().toInt()
                        val regionX = regionId shr 8
                        val regionY = regionId and 255
                        val objects = maps.add("l${regionX}_${regionY}", intArrayOf(0, 0, 0, 0))
                        val floors = maps.add("m${regionX}_${regionY}")
                        objects.add(0, File(mapPackerModel.landscapeFile.get()).readBytes())
                        floors.add(0, File(mapPackerModel.floorFile.get()).readBytes())
                        maps.update()
                    }
                }
            }
        }
    }

}