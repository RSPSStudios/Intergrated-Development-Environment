package com.javatar.plugin.definition.editor.managers

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.definition.DefinitionProvider
import com.javatar.osrs.definitions.impl.TextureDefinition
import com.javatar.osrs.definitions.loaders.TextureLoader

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

class TextureProvider(val cache: CacheLibrary) : DefinitionProvider<TextureDefinition> {

    val textures = ConfigDefinitionManager(TextureLoader())

    override fun getDefinition(id: Int): TextureDefinition {
        val data = cache.data(9, 0, id)
        if (data != null) return textures.load(id, data)
        return textures[id]!!
    }

    override fun values(): List<TextureDefinition> {
        return textures.definitions.values.toList()
    }
}
