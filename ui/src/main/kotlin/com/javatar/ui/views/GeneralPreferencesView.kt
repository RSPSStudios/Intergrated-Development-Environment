package com.javatar.ui.views

import com.javatar.ui.models.GeneralPreferencesModel
import javafx.geometry.Pos
import tornadofx.*

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 16 2021
 */

class GeneralPreferencesView : Fragment() {

    val model: GeneralPreferencesModel by inject()

    override val root = form {
        alignment = Pos.CENTER
        fieldset("Notification Settings") {
            field("Hide User Events") {
                checkbox(property = model.ignoreUserEvents)
            }
            field("Hide Information Events") {
                checkbox(property = model.ignoreInformationEvents)
            }
            field("Hide Warning Events") {
                checkbox(property = model.ignoreWarningEvents)
            }
            field("Hide Error Events") {
                checkbox(property = model.ignoreErrorEvents)
            }
        }
    }
}
