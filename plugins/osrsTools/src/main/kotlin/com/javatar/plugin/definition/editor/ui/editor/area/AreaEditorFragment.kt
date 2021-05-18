package com.javatar.plugin.definition.editor.ui.editor.area

import com.javatar.osrs.tools.SpriteTools.toImage
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.ui.editor.area.model.AreaEditorModel
import com.javatar.plugin.definition.editor.ui.editor.getSprite
import tornadofx.*

class AreaEditorFragment : Fragment("Area Editor") {

    val areaModel: AreaEditorModel by inject()

    val sprites = OldSchoolDefinitionManager.sprites

    override val root = hbox {
        spacing = 10.0
        style = "-fx-base: #3f474f;"
        listview(areaModel.areas) {
            areaModel.selected.bind(selectionModel.selectedItemProperty())
            cellFormat {
                text = "Area ${it.id}"
            }
        }
        scrollpane {
            form {
                fieldset("Sprites") {
                    vbox {
                        dynamicContent(areaModel.selected) {
                            if (it != null) {
                                field {
                                    val cache = areaModel.cache.get()
                                    if (cache != null) {
                                        hbox {
                                            spacing = 20.0
                                            val sprite = cache.getSprite(it.spriteId)
                                            if(sprite.pixels != null) {
                                                imageview(sprite.toImage())
                                            } else {
                                                label("No Sprite")
                                            }
                                            val sprite2 = cache.getSprite(it.field3294)
                                            if(sprite2.pixels != null) {
                                                imageview(sprite2.toImage())
                                            } else {
                                                label("No Sprite")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                fieldset("Configs") {
                    field("Name") {
                        textfield(areaModel.name)
                    }
                    field("Target Name") {
                        textfield(areaModel.targetName)
                    }
                    fieldset("") {

                    }
                }
                fieldset("Menu Actions") {
                    listview(areaModel.menuActions) {
                        cellFormat {
                            text = it
                        }
                    }
                }
            }
        }
    }
}