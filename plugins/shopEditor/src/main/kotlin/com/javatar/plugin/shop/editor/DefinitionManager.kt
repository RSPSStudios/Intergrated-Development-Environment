package com.javatar.plugin.shop.editor

import com.google.gson.GsonBuilder
import com.javatar.osrs.definitions.loaders.*

object DefinitionManager {

    val gson = GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .create()

    val items = ConfigDefinitionManager(ItemLoader())
    val npcs = ConfigDefinitionManager(NpcLoader())
    val models = ConfigDefinitionManager(ModelLoader())
    val sprites = ConfigDefinitionManager(SpriteLoader())
    val textures = ConfigDefinitionManager(TextureLoader())

}
