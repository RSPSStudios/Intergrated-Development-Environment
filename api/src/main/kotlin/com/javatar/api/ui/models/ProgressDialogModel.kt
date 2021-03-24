package com.javatar.api.ui.models

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 23 2021
 */

class ProgressDialogModel : ViewModel() {

    val progress = bind { SimpleDoubleProperty(this, "progress", 0.0) }

    val msg = bind { SimpleStringProperty(this, "msg", "") }

}
