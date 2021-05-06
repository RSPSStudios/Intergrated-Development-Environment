package com.javatar.plugin.definition.editor.managers

import com.javatar.osrs.definitions.Definition
import com.javatar.osrs.definitions.DefinitionManager
import com.javatar.osrs.definitions.DeserializeDefinition
import com.javatar.osrs.definitions.impl.ItemDefinition

class ConfigDefinitionManager<T : Definition, L : DeserializeDefinition<T>>(val loader: L) : DefinitionManager<T> {
    
    val definitions = mutableMapOf<Int, T>()

    val modifiedDefinitions = mutableMapOf<Int, Int>()

    val nextId: Int
        get() = (definitions.keys.maxOrNull() ?: 1) + 1

    override fun add(p0: T) {
        definitions[p0.definitionId] = p0
    }

    override fun remove(p0: T) {
        definitions.remove(p0.definitionId)
    }

    override fun load(p0: Int, p1: ByteArray): T {
        val def = loader.deserialize(p0, p1)
        add(def)
        return def
    }

    override fun get(p0: Int): T? {
        return definitions[p0]
    }
}