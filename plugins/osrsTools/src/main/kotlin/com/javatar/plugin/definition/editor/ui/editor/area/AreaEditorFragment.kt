package com.javatar.plugin.definition.editor.ui.editor.area

import com.javatar.osrs.definitions.loaders.SpriteLoader
import com.javatar.osrs.tools.SpriteTools.toImage
import com.javatar.plugin.definition.editor.managers.ConfigDefinitionManager
import com.javatar.plugin.definition.editor.ui.editor.area.model.AreaEditorModel
import com.javatar.plugin.definition.editor.ui.editor.getSprite
import com.javatar.plugin.definition.editor.ui.rsmenu.ActionEditorFragment
import com.javatar.plugin.definition.editor.ui.selectors.SelectSpriteFragment
import com.javatar.plugin.definition.editor.ui.selectors.scope.SpriteSelectScope
import javafx.beans.binding.Bindings
import javafx.collections.FXCollections
import tornadofx.*

class AreaEditorFragment : Fragment("Area Editor") {

    val areaModel: AreaEditorModel by inject()

    val sprites = ConfigDefinitionManager(SpriteLoader())

    override val root = hbox {
        spacing = 10.0
        style = "-fx-base: #3f474f;"
        listview(areaModel.areas) {
            areaModel.selected.bind(selectionModel.selectedItemProperty())
            cellFormat {
                text = "Area ${it.id}"
            }
        }
        form {
            fieldset("Sprites") {
                vbox {
                    dynamicContent(areaModel.selected) { s ->
                        if (s != null) {
                            field {
                                val cache = areaModel.cache.get()
                                if (cache != null) {
                                    hbox {
                                        spacing = 20.0
                                        vbox {
                                            spacing = 10.0
                                            dynamicContent(areaModel.spriteId) {
                                                val sprite = cache.getSprite(s.spriteId)
                                                if (sprite.pixels != null) {
                                                    imageview(sprite.toImage())
                                                } else {
                                                    label("No Sprite")
                                                }
                                                button("Select Sprite").action {
                                                    val scope = SpriteSelectScope(cache)
                                                    val dialog = find<SelectSpriteFragment>(scope)
                                                    dialog.openModal(block = true)
                                                    if (dialog.selectModel.selected.get() != null) {
                                                        val newSprite = dialog.selectModel.selected.get()
                                                        areaModel.spriteId.set(newSprite.id)
                                                    }
                                                }
                                            }
                                        }
                                        vbox {
                                            spacing = 10.0
                                            dynamicContent(areaModel.spriteId2) {
                                                val sprite2 = cache.getSprite(s.field3294)
                                                if (sprite2.pixels != null) {
                                                    imageview(sprite2.toImage())
                                                } else {
                                                    label("No Sprite")
                                                }
                                                button("Select Sprite").action {
                                                    val scope = SpriteSelectScope(cache)
                                                    val dialog = find<SelectSpriteFragment>(scope)
                                                    dialog.openModal(block = true)
                                                    if (dialog.selectModel.selected.get() != null) {
                                                        val newSprite = dialog.selectModel.selected.get()
                                                        areaModel.spriteId2.set(newSprite.id)
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
            fieldset("Configs") {
                field("Name") {
                    textfield(areaModel.name)
                }
                field("Target Name") {
                    textfield(areaModel.targetName)
                }
            }
            fieldset("Menu Actions") {
                add<ActionEditorFragment> {
                    actionModel.actions.bind(Bindings.createObjectBinding({
                        val list = FXCollections.observableArrayList(areaModel.menuActions)
                        list.add("Cancel")
                        list.replaceAll {
                            it ?: "null"
                        }
                        list
                    }, areaModel.menuActions))
                    actionModel.targetName.bind(areaModel.targetName)
                    actionModel.maxOptions.set(areaModel.menuActions.size)
                    setOnSave {
                        val list = FXCollections.observableArrayList(actionModel.actions)
                        list.remove("Cancel")
                        list.replaceAll { if (it == "null") null else it }
                        areaModel.menuActions.setAll(list)
                    }
                }
            }
        }
    }
}