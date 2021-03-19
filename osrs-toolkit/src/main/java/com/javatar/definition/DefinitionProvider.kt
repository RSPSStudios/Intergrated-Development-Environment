package com.javatar.definition

import com.javatar.osrs.definitions.Definition

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

interface DefinitionProvider<T : Definition> {

    fun getDefinition(id: Int): T
    fun values(): List<T>

}
