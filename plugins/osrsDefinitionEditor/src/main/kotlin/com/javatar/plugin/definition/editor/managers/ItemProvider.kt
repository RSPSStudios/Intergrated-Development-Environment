package com.javatar.plugin.definition.editor.managers

import com.displee.cache.CacheLibrary
import com.javatar.ItemDefinition
import com.javatar.definition.DefinitionProvider
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager

class ItemProvider(val cache: CacheLibrary) : DefinitionProvider<ItemDefinition> {
    val items = OldSchoolDefinitionManager.items
    override fun getDefinition(id: Int): ItemDefinition {
        val data = cache.data(2, 10, id)
        if (data != null) {
            return items.load(id, data)
        }
        return items.getDefinition(id)
    }

    override fun values(): List<ItemDefinition> {
        return items.values()
    }
}
