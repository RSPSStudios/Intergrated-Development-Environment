package com.javatar.plugin.definition.editor.ui.editor

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.impl.SpriteDefinition
import com.javatar.osrs.definitions.loaders.SpriteLoader
import com.javatar.plugin.definition.editor.managers.ConfigDefinitionManager

fun CacheLibrary.getSprite(spriteId: Int): SpriteDefinition {
    val sprites = ConfigDefinitionManager(SpriteLoader())
    val data = data(8, spriteId)
    if (sprites.definitions.containsKey(spriteId)) {
        return sprites[spriteId]!!.sprites[0]
    }
    if (data != null) {
        return sprites.load(spriteId, data).sprites[0]
    }
    return SpriteDefinition().apply {
        width = 1
        height = 1
    }
}