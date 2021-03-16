package com.javatar.plugin.shop.editor

import com.javatar.api.ui.PreferencesExtension
import com.javatar.plugin.shop.editor.ui.preferences.PreferencesView
import org.pf4j.Extension
import tornadofx.Fragment
import tornadofx.label

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 16 2021
 */
@Extension
class Preferences : PreferencesExtension, Fragment() {

    override val root = label(name())

    override fun createPreferencesUI(): Fragment {
        return PreferencesView()
    }

    override fun name(): String {
        return "Shop Editor Preferences"
    }
}
