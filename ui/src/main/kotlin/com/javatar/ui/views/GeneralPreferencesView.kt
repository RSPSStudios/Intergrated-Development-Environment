package com.javatar.ui.views

import javafx.geometry.Pos
import tornadofx.Fragment
import tornadofx.label
import tornadofx.vbox

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 16 2021
 */

class GeneralPreferencesView : Fragment() {
    override val root = vbox {
        alignment = Pos.CENTER
        spacing = 10.0

        label("No Preferences Available")
    }
}
