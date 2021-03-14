package com.javatar.plugin.definition.editor.managers

import com.displee.cache.CacheLibrary
import com.javatar.TextureDefinition
import com.javatar.definition.DefinitionProvider
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

class TextureProvider(val cache: CacheLibrary) : DefinitionProvider<TextureDefinition> {

    val textures = OldSchoolDefinitionManager.textures

    override fun getDefinition(id: Int): TextureDefinition {
        val data = cache.data(9, 0, id)
        if (data != null) return textures.load(id, data)
        return textures.getDefinition(id)
    }

    override fun values(): List<TextureDefinition> {
        return textures.values()
    }
}
