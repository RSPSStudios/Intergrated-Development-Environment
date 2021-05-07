package com.javatar.plugin.definition.editor.ui.editor.sprites.models

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.impl.SpriteGroupDefinition
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel

class SpriteGridModel : ViewModel() {

    val sprites = bind { SimpleListProperty<SpriteGroupDefinition>(this, "sprites", FXCollections.observableArrayList()) }

    val selectedSprite = bind { SimpleObjectProperty<SpriteGroupDefinition>(this, "selected_sprite") }

    val cache = SimpleObjectProperty<CacheLibrary>(this, "cache")

}