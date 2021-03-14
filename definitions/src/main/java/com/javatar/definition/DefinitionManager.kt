package com.javatar.definition

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

class DefinitionManager<T : Definition, L : DeserializeDefinition<T>, S : SerializableDefinition<T>>(
    val loader: L,
    val saver: S
) {

    val definitions = mutableMapOf<Int, T>()

    fun add(def: T) {
        definitions[def.definitionId] = def
    }

    fun remove(id: Int) {
        definitions.remove(id)
    }

    fun load(id: Int, data: ByteArray): T {
        val definition = loader.deserialize(id, data)
        add(definition)
        return definition
    }

    fun save(def: T): ByteArray {
        return saver.serialize(def)
    }

}
