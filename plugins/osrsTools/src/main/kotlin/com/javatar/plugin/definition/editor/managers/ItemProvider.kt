package com.javatar.plugin.definition.editor.managers

import com.displee.cache.CacheLibrary
import com.javatar.definition.DefinitionProvider
import com.javatar.osrs.definitions.impl.ItemDefinition
import com.javatar.osrs.definitions.loaders.ItemLoader
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager

class ItemProvider(val cache: CacheLibrary) : DefinitionProvider<ItemDefinition> {

    val items = mutableMapOf<Int, ItemDefinition>()
    val loader = ItemLoader()

    override fun getDefinition(id: Int): ItemDefinition {
        if(items.containsKey(id)) {
            return items[id]!!
        }
        val data = cache.data(2, 10, id)
        if(data != null) {
            val def = loader.load(id, data)
            items[def.id] = def
            return def
        }
        return ItemDefinition(-1)
    }

    override fun values(): List<ItemDefinition> {
        return items.values.toList()
    }
}
