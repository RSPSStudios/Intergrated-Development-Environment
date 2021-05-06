package com.javatar.plugin.definition.editor.managers

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.definition.DefinitionProvider
import com.javatar.osrs.definitions.impl.ItemDefinition
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager

class ItemProvider(val cache: CacheLibrary) : DefinitionProvider<ItemDefinition> {

    val items = OldSchoolDefinitionManager.items

    override fun getDefinition(id: Int): ItemDefinition {
        if(!items.definitions.containsKey(id)) {
            val data = cache.data(2, 10, id)
            if(data != null) {

                val def = items.load(id, data)

                if(def.id == 3) {
                    println(def.colorFind.toTypedArray().contentDeepToString())
                    println(def.colorReplace.toTypedArray().contentDeepToString())
                }

                return def
            }
        } else if(items.definitions.containsKey(id)) {
            return items[id]!!
        }
        return ItemDefinition(-1)
    }

    override fun values(): List<ItemDefinition> {
        return items.definitions.values.toList()
    }
}
