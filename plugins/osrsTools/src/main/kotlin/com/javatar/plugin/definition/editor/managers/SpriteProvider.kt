package com.javatar.plugin.definition.editor.managers

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.definition.DefinitionProvider
import com.javatar.osrs.definitions.impl.SpriteGroupDefinition
import com.javatar.osrs.definitions.loaders.SpriteLoader

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

class SpriteProvider(val cache: CacheLibrary) : DefinitionProvider<SpriteGroupDefinition> {

    val sprites = ConfigDefinitionManager(SpriteLoader())

    override fun getDefinition(id: Int): SpriteGroupDefinition {
        val data = cache.data(8, id)
        if (data != null) sprites.load(id, data)
        val sprite = sprites[id]
        if (sprite == null) {
            val group = SpriteGroupDefinition()
            group.width = 1
            group.height = 1
            return SpriteGroupDefinition()
        }
        if (sprite.width <= 0) {
            sprite.width = 32
        }
        if (sprite.height <= 0) {
            sprite.height = 32
        }
        return sprite
    }

    override fun values(): List<SpriteGroupDefinition> {
        return sprites.definitions.values.toList()
    }
}
