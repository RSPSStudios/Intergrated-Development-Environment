package com.javatar.plugin.shop.editor.providers

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.definition.DefinitionProvider
import com.javatar.osrs.definitions.impl.SpriteGroupDefinition
import com.javatar.plugin.shop.editor.DefinitionManager

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

class SpriteProvider(val cache: CacheLibrary) : DefinitionProvider<SpriteGroupDefinition> {

    val sprites = DefinitionManager.sprites

    override fun getDefinition(id: Int): SpriteGroupDefinition {
        val data = cache.data(8, id)
        if (data != null) sprites.load(id, data)
        return sprites[id]!!
    }

    override fun values(): List<SpriteGroupDefinition> {
        return sprites.definitions.values.toList()
    }
}
