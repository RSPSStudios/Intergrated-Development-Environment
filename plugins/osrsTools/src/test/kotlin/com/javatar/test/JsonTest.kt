package com.javatar.test

import com.google.gson.GsonBuilder
import com.javatar.osrs.definitions.impl.ItemDefinition
import org.junit.jupiter.api.Test

class JsonTest {

    @Test
    fun test() {

        val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
        val item = ItemDefinition(4151)
        item.name = "Abyssal Whip"
        item.colorFind = shortArrayOf(0, 2, 3)
        item.params = mutableMapOf()
        item.params[5] = "Test"

        val obj = gson.toJsonTree(item).asJsonObject

        obj.entrySet().forEach {
            val key = it.key
            val value = it.value
            if(value.isJsonArray) {
                println("$key - Sick")
            } else if(value.isJsonObject) {
                println("$key - Object")
            } else if(value.isJsonPrimitive) {
                println("$key - Primitive")
            } else if(value.isJsonNull) {
                println("$key - NULL")
            } else {
                println("$key - Unknown?")
            }
        }

    }

}