package com.javatar.plugin.shop.editor.providers

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.definition.DefinitionProvider
import com.javatar.osrs.definitions.impl.ModelDefinition
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
        return models[id]!!
    }

    override fun values(): List<ModelDefinition> {
        return models.definitions.values.toList()
    }
}
