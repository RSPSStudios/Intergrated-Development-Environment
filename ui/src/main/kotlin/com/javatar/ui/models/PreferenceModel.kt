package com.javatar.ui.models

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.Fragment
import tornadofx.ViewModel

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 16 2021
 */

class PreferenceModel(value: String = "", node: Fragment? = null) : ViewModel() {

    val name = bind { SimpleStringProperty(this, "name", value) }

    val node = bind { SimpleObjectProperty<Fragment>(this, "node", node) }

}
