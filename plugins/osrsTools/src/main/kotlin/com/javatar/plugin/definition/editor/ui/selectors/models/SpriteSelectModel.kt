package com.javatar.plugin.definition.editor.ui.selectors.models

import com.javatar.osrs.definitions.impl.SpriteDefinition
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

class SpriteSelectModel : ViewModel() {

    val sprites = bind { SimpleListProperty<SpriteDefinition>(this, "sprites", FXCollections.observableArrayList()) }

    val selected = bind { SimpleObjectProperty<SpriteDefinition>(this, "selected") }

}