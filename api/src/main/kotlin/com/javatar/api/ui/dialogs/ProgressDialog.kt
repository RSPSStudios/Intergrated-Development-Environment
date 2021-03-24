package com.javatar.api.ui.dialogs

import com.javatar.api.ui.models.ProgressDialogModel
import javafx.geometry.Pos
import tornadofx.Fragment
import tornadofx.hbox
import tornadofx.progressbar
import tornadofx.progressindicator

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 23 2021
 */

class ProgressDialog : Fragment() {

    val progressModel: ProgressDialogModel by inject()

    override val root = hbox {
        style = "-fx-base: #3f474f;"
        alignment = Pos.CENTER
        spacing = 10.0
        progressbar(progressModel.progress)
        progressindicator(progressModel.progress)
    }

}
