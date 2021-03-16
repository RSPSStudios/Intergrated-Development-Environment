package com.javatar.plugin.shop.editor.ui.models

import javafx.beans.property.SimpleBooleanProperty
import tornadofx.ViewModel

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 16 2021
 */

class PreferenceModel : ViewModel() {

    val disableIcons = bind { SimpleBooleanProperty(this, "disable_icons", config.boolean("DISABLE_ICONS") ?: false) }
    val warnOnDelete = bind { SimpleBooleanProperty(this, "warn_on_delete", config.boolean("WARN_ON_DELETE") ?: true) }

    override fun onCommit() {
        super.onCommit()
        with(config) {
            set("WARN_ON_DELETE", warnOnDelete.get())
            set("DISABLE_ICONS", disableIcons.get())
            save()
        }
    }
}
