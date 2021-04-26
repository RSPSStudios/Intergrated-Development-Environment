package com.javatar.api.ui

import javafx.fxml.FXMLLoader
import javafx.scene.Node
import tornadofx.UIComponent
import java.net.URL


fun <T : Node> UIComponent.loadPluginFXML(location: String, root: Any? = null): T {
    fxmlLoader = FXMLLoader(URL(location)).apply {
        resources = messages
        if (root != null) setRoot(root)
        setController(this@loadPluginFXML)
    }
    return fxmlLoader.load()
}