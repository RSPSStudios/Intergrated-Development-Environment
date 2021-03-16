package com.javatar.plugin.shop.editor.providers

import com.displee.cache.CacheLibrary
import com.javatar.ModelDefinition
import com.javatar.definition.DefinitionProvider
import com.javatar.plugin.shop.editor.DefinitionManager

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

class ModelProvider(val cache: CacheLibrary) : DefinitionProvider<ModelDefinition> {

    val models = DefinitionManager.models

    override fun getDefinition(id: Int): ModelDefinition {
        val data = cache.data(7, id)
        if (data != null) return models.load(id, data)
        return models.getDefinition(id)
    }

    override fun values(): List<ModelDefinition> {
        return models.values()
    }
}
