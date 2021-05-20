package com.javatar.plugin.definition.editor.ui.editor.world.objects

import com.javatar.osrs.definitions.impl.ObjectDefinition
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.ui.editor.world.objects.models.ObjectEditorModel
import tornadofx.*

class ObjectEditorFragment : Fragment("World Object Editor") {

    val model: ObjectEditorModel by inject()

    val objects = OldSchoolDefinitionManager.objects

    init {
        model.cache.onChange {
            if(it != null) {
                loadObjects()
            }
        }
        model.searchText.onChange {
            if(it == null || it.isEmpty()) {
                model.objects.setAll(objects.definitions.values)
            }
        }
    }

    override val root = hbox {
        style {
            baseColor = c("#3f474f")
        }
        vbox {
            textfield(model.searchText) {
                action {
                    searchObjects(text)
                }
            }
            listview(model.objects) {
                model.selected.bind(selectionModel.selectedItemProperty())
                cellFormat {
                    text = if(it.name == null || it.name == "null") {
                        "Object ${it.id}"
                    } else {
                        "${it.name} - ${it.id}"
                    }
                }
            }
        }

    }

    private fun loadObjects() {
        val cache = model.cache.get()
        if (cache != null) {
            val objectIds = cache.index(2).archive(6)?.fileIds() ?: intArrayOf()
            val list = mutableListOf<ObjectDefinition>()
            for (objectId in objectIds) {
                val data = cache.data(2, 6, objectId)
                if (data != null) {
                    list.add(
                        objects.load(objectId, data)
                    )
                }
            }
            model.objects.setAll(list)
        }
    }

    private fun searchObjects(text: String) {
        if(text.isEmpty()) {
            model.objects.setAll(objects.definitions.values)
            return
        }
        when {
            text.endsWith(":var") -> {
                val identifier = text.split(":")[0]
                val list = if(identifier.isNotEmpty()) {
                    val id = text.split(":")[0].toInt()
                    objects.definitions.values.filter {
                        it.varbitID == id || it.varpID == id
                    }
                } else {
                    objects.definitions.values.filter {
                        it.varbitID != -1 || it.varpID != -1
                    }
                }
                model.objects.setAll(list)
            }
            text.endsWith(":anim") -> {
                val identifier = text.split(":")[0]
                val list = if (identifier.isNotEmpty()) {
                    val id = text.split(":")[0].toInt()
                    objects.definitions.values.filter {
                        it.animationID == id
                    }
                } else {
                    objects.definitions.values.filter {
                        it.animationID != -1
                    }
                }
                model.objects.setAll(list)
            }
            text.endsWith(":sound") -> {
                val identifier = text.split(":")[0]
                val list = if (identifier.isNotEmpty()) {
                    val id = text.split(":")[0].toInt()
                    objects.definitions.values.filter {
                        it.ambientSoundId == id || (it.soundEffectIds != null && id in it.soundEffectIds)
                    }
                } else {
                    objects.definitions.values.filter {
                        it.ambientSoundId != -1 || (it.soundEffectIds != null && it.soundEffectIds.isNotEmpty())
                    }
                }
                model.objects.setAll(list)
            }
            text.isInt() -> {
                val id = text.toInt()
                model.objects.setAll(objects.definitions[id])
            }
            else -> {
                val list = objects.definitions.values.filter {
                    it.name != null && it.name.lowercase().contains(text.lowercase())
                }
                model.objects.setAll(list)
            }
        }
    }

}