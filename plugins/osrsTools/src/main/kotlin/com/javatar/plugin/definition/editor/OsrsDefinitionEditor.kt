package com.javatar.plugin.definition.editor

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.pf4j.Plugin
import org.pf4j.PluginWrapper

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

class OsrsDefinitionEditor(wrapper: PluginWrapper?) : Plugin(wrapper) {
    override fun start() {
        super.start()
        println("Starting Old School Definition Editor")
        properties["pluginPath"] = "file:${wrapper.pluginPath.toAbsolutePath()}/classes"
    }

    companion object {
        val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()

        val properties: MutableMap<String, String> = mutableMapOf()

        inline fun <reified T> Gson.fromJson(data: String): T {
            return fromJson(data, T::class.java)
        }
    }
}
