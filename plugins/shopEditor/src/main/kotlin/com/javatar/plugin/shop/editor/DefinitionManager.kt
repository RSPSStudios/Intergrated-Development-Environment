package com.javatar.plugin.shop.editor

import com.google.gson.GsonBuilder
import com.javatar.osrs.definitions.DefinitionManager
import com.javatar.osrs.definitions.loaders.*

object DefinitionManager {

    val gson = GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .create()

    val items = DefinitionManager(ItemLoader())
    val npcs = DefinitionManager(NpcLoader())
    val models = DefinitionManager(ModelLoader())
    val sprites = DefinitionManager(SpriteLoader())
    val textures = DefinitionManager(TextureLoader())

}
