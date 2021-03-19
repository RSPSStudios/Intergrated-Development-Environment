package com.javatar.plugin.definition.editor.managers

import com.displee.cache.CacheLibrary
import com.javatar.definition.DefinitionProvider
import com.javatar.osrs.definitions.impl.ModelDefinition
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

class ModelProvider(val cache: CacheLibrary) : DefinitionProvider<ModelDefinition> {

    val models = OldSchoolDefinitionManager.models

    override fun getDefinition(id: Int): ModelDefinition {
        val data = cache.data(7, id)
        if (data != null) return models.load(id, data)
        return models.getDefinition(id)
    }

    override fun values(): List<ModelDefinition> {
        return models.values()
    }
}
