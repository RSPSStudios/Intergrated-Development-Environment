package com.javatar.plugin.definition.editor.ui.selectors.models

import com.javatar.osrs.definitions.impl.TextureDefinition
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

class TextureSelectModel : ViewModel() {
    val textures = bind { SimpleListProperty<TextureDefinition>(this, "textures", FXCollections.observableArrayList()) }
    val selected = bind { SimpleObjectProperty<TextureDefinition>(this, "selected") }
}