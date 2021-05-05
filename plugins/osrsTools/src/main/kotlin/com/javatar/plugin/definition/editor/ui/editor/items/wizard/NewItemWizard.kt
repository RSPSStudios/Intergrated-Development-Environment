package com.javatar.plugin.definition.editor.ui.editor.items.wizard

import com.javatar.plugin.definition.editor.ui.editor.items.model.ItemCreationModel
import javafx.beans.binding.BooleanExpression
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class NewItemWizard : Wizard("New Item", "Enter item information") {

    val def: ItemCreationModel by inject()

    override val canGoNext: BooleanExpression = currentPageComplete
    override val canFinish: BooleanExpression = allPagesComplete

    init {
        add(BasicItemInfo::class)
        add(SecondaryItemGenerationInfo::class)
    }

}

class WizardStyles : Stylesheet() {
    companion object {
        val wizard by cssclass()
        val header by cssclass()
        val stepInfo by cssclass()
        val stepsHeading by cssclass()
        val heading by cssclass()
        val graphic by cssclass()
        val content by cssclass()
        val buttons by cssclass()
        val bold by cssclass()
    }

    init {
        wizard {

            root {
                baseColor = Color.RED
            }

            hyperlink {
                borderStyle += BorderStrokeStyle.NONE
                borderWidth += box(0.px)
                underline = false
            }
            hyperlink and visited {
                unsafe("-fx-text-fill", raw("-fx-accent"))

            }
            hyperlink and visited and hover {
                unsafe("-fx-text-fill", raw("-fx-accent"))
            }
            hyperlink and disabled {
                textFill = Color.BLACK
                opacity = 1.0
            }
            bold {
                fontWeight = FontWeight.BOLD
            }
            stepInfo {
                padding = box(15.px)
                stepsHeading {
                    fontWeight = FontWeight.BOLD
                    underline = true
                    padding = box(15.px, 0.px)
                }
            }
            header {
                label {
                    fontSize = 16.px
                    fontWeight = FontWeight.BOLD
                }
                heading {
                    fontSize = 12.px
                    fontWeight = FontWeight.NORMAL
                }
                padding = box(10.px)
                spacing = 10.px
                borderColor += box(Color.TRANSPARENT, Color.TRANSPARENT, Color.LIGHTGRAY, Color.TRANSPARENT)
                graphic {
                    prefWidth = 48.px
                    prefHeight = 48.px
                }
            }
            content {
                padding = box(10.px)
            }
            buttons {
                padding = box(10.px)
                borderColor += box(Color.LIGHTGRAY, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT)
            }
        }
    }
}