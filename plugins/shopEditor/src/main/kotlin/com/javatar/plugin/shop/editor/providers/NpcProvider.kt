package com.javatar.plugin.shop.editor.providers

import com.displee.cache.CacheLibrary
import com.javatar.definition.DefinitionProvider
import com.javatar.osrs.definitions.impl.NpcDefinition
import com.javatar.plugin.shop.editor.DefinitionManager

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

class NpcProvider(val cache: CacheLibrary) : DefinitionProvider<NpcDefinition> {

    val npcs = DefinitionManager.npcs

    override fun getDefinition(id: Int): NpcDefinition {
        val data = cache.data(2, 9, id)
        if (data != null) return npcs.load(id, data)
        return npcs.getDefinition(id)
    }

    fun npcIds(): List<Int> {
        return (cache.index(2).archive(9)?.fileIds() ?: intArrayOf()).toList()
    }

    override fun values(): List<NpcDefinition> {
        return npcs.values()
    }
}
