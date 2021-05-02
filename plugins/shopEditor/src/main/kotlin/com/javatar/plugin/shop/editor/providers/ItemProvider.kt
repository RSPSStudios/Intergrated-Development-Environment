package com.javatar.plugin.shop.editor.providers

import com.displee.cache.CacheLibrary
import com.javatar.definition.DefinitionProvider
import com.javatar.osrs.definitions.impl.ItemDefinition
import com.javatar.osrs.definitions.loaders.ItemLoader
import com.javatar.plugin.shop.editor.DefinitionManager

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

class ItemProvider(val cache: CacheLibrary) : DefinitionProvider<ItemDefinition> {

    val items = DefinitionManager.items
    override fun getDefinition(id: Int): ItemDefinition {
        if(items[id] == null) {
            val data = cache.data(2, 10, id)
            if(data != null) {
                return items.load(id, data)
            }
        }
        return items[id]!!
    }

    override fun values(): List<ItemDefinition> {
        return items.definitions.values.toList()
    }

    fun itemIds(): List<Int> {
        return cache.index(2).archive(10)?.fileIds()?.toList() ?: emptyList()
    }
}
