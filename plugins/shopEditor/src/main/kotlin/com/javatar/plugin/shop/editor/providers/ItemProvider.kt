package com.javatar.plugin.shop.editor.providers

import com.displee.cache.CacheLibrary
import com.javatar.definition.DefinitionProvider
import com.javatar.osrs.definitions.impl.ItemDefinition
import com.javatar.plugin.shop.editor.DefinitionManager

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

class ItemProvider(val cache: CacheLibrary) : DefinitionProvider<ItemDefinition> {

    val items = DefinitionManager.items

    override fun getDefinition(id: Int): ItemDefinition {
        val data = cache.data(2, 10, id)
        if (data != null) return items.load(id, data)
        return items.getDefinition(id)
    }

    override fun values(): List<ItemDefinition> {
        return items.values()
    }

    fun itemIds(): List<Int> {
        return cache.index(2).archive(10)?.fileIds()?.toList() ?: emptyList()
    }
}
