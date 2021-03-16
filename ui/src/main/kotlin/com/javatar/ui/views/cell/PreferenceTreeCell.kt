package com.javatar.ui.views.cell

import com.javatar.ui.models.PreferenceModel
import javafx.scene.control.TreeCell

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 16 2021
 */

class PreferenceTreeCell : TreeCell<PreferenceModel>() {

    override fun updateItem(item: PreferenceModel?, empty: Boolean) {
        super.updateItem(item, empty)
        if (!empty && item != null) {
            textProperty().bind(item.name)
        }
    }
}
