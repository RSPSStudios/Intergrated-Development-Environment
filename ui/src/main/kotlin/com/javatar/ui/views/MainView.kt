package com.javatar.ui.views

import com.javatar.ui.models.CacheConfigurationModel
import com.javatar.ui.views.fs.FileSystemView
import javafx.scene.control.Alert
import javafx.scene.layout.AnchorPane
import tornadofx.View
import tornadofx.alert
import tornadofx.chooseDirectory

class MainView : View() {

    override val root: AnchorPane by fxml("main-view.fxml")

    val fsView: AnchorPane by fxid()

    val configModel: CacheConfigurationModel by di()

    init {

        val cache = configModel.customCache.get()
        if (cache != null) {
            fsView.add(FileSystemView())
        }
    }

    fun loadCustomCache() {
        val dir = chooseDirectory("Choose Custom Cache Directory")
        if (dir != null && dir.exists()) {
            configModel.customCachePath.set(dir.absolutePath)
            configModel.commit(configModel.customCachePath)
        } else {
            alert(Alert.AlertType.ERROR, "Could not find cache.").show()
        }
    }

    fun loadOsrsCache() {
        val dir = chooseDirectory("Choose Custom Cache Directory")
        if (dir != null && dir.exists()) {
            configModel.osrsCachePath.set(dir.absolutePath)
            configModel.commit(configModel.osrsCachePath)
        } else {
            alert(Alert.AlertType.ERROR, "Could not find cache.").show()
        }
    }

}
