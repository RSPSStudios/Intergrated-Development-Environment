package com.javatar.plugin.shop.editor.ui.preferences

import com.javatar.plugin.shop.editor.ui.models.PreferenceModel
import javafx.geometry.Pos
import tornadofx.Fragment
import tornadofx.checkbox
import tornadofx.vbox

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 16 2021
 */

class PreferencesView : Fragment() {

    val prefModel: PreferenceModel by inject()

    override val root = vbox {
        alignment = Pos.CENTER
        spacing = 10.0

        checkbox("Ask before deletion", prefModel.warnOnDelete)

    }

}
