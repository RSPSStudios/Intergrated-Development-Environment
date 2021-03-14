package com.javatar.plugin.definition.editor.managers

import com.displee.cache.CacheLibrary
import com.javatar.SpriteGroupDefinition
import com.javatar.definition.DefinitionProvider
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

class SpriteProvider(val cache: CacheLibrary) : DefinitionProvider<SpriteGroupDefinition> {

    val sprites = OldSchoolDefinitionManager.sprites

    override fun getDefinition(id: Int): SpriteGroupDefinition {
        val data = cache.data(8, id)
        if (data != null) sprites.load(id, data)
        return sprites.getDefinition(id)
    }

    override fun values(): List<SpriteGroupDefinition> {
        return sprites.values()
    }
}
