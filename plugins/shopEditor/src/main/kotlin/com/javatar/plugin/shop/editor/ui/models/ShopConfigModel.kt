package com.javatar.plugin.shop.editor.ui.models

import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

class ShopConfigModel : ViewModel() {

    val shopFilesPath = bind { SimpleStringProperty(this, "shops_path", config.string("CONFIG_DIR")) }


    override fun onCommit() {
        super.onCommit()
        with(config) {
            if (shopFilesPath.get() != null) {
                set("CONFIG_DIR", shopFilesPath.get())
                save()
            }
        }
    }
}
