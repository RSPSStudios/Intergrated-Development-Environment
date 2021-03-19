package com.javatar.plugin.shop.editor

import com.google.gson.GsonBuilder
import com.javatar.definition.DefinitionManager
import com.javatar.osrs.definitions.loaders.*
import com.javatar.saver.*

object DefinitionManager {

    val gson = GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .create()

    val items = DefinitionManager(ItemLoader(), ItemSaver())
    val npcs = DefinitionManager(NpcLoader(), NpcSaver())
    val models = DefinitionManager(ModelLoader(), ModelSaver())
    val sprites = DefinitionManager(SpriteLoader(), SpriteSaver())
    val textures = DefinitionManager(TextureLoader(), TextureSaver())

}
