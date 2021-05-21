package com.javatar.plugin.definition.editor.ui.editor.world.objects.tabs

import com.javatar.plugin.definition.editor.ui.NumberSpinnerValueFactory
import com.javatar.plugin.definition.editor.ui.editor.world.objects.models.ObjectEditorModel
import tornadofx.*

class ObjectConfigsFragment : Fragment("Object Configurations") {

    val model: ObjectEditorModel by inject()

    override val root = form {
        fieldset("Object Values") {
            field("Name") {
                textfield(model.name)
            }
            field("Ambient") {
                spinner(
                    editable = true,
                    property = model.ambient,
                    valueFactory = NumberSpinnerValueFactory(0, 255)
                ) {
                    editor.stripNonNumeric()
                }
            }
            field("Contrast") {
                spinner(
                    editable = true,
                    property = model.contrast,
                    valueFactory = NumberSpinnerValueFactory(0, 255)
                ) {
                    editor.stripNonNumeric()
                }
            }
            field("Interaction Type") {
                spinner(
                    editable = true,
                    property = model.interactType,
                    valueFactory = NumberSpinnerValueFactory(0, 255)
                ) {
                    editor.stripNonNumeric()
                }
            }
            field("Contoured Ground") {
                spinner(
                    editable = true,
                    property = model.contouredGround,
                    valueFactory = NumberSpinnerValueFactory(0, 255)
                ) {
                    editor.stripNonNumeric()
                }
            }
        }
        fieldset("Object Flags") {
            field("Hollow") {
                checkbox(property = model.isHollow)
            }
            field("Merge Normals") {
                checkbox(property = model.mergeNormals)
            }
            field("Shadow") {
                checkbox(property = model.shadow)
            }
            field("Obstructs Ground") {
                checkbox(property = model.obstructsGround)
            }
            field("Blocks Projectiles") {
                checkbox(property = model.blocksProjectile)
            }
            field("Randomize Animation Start") {
                checkbox(property = model.randomizeAnimationStart)
            }
            field("Rotated") {
                checkbox(property = model.isRotated)
            }
        }

    }

}