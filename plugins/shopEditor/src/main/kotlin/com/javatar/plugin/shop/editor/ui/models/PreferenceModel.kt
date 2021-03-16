package com.javatar.plugin.shop.editor.ui.models

import javafx.beans.property.SimpleBooleanProperty
import tornadofx.ViewModel

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 16 2021
 */

class PreferenceModel : ViewModel() {

    val warnOnDelete = bind { SimpleBooleanProperty(this, "warn_on_delete", true) }

}
