package com.javatar.plugin.shop.editor

import org.pf4j.Plugin
import org.pf4j.PluginWrapper

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

class ShopEditor(wrapper: PluginWrapper) : Plugin(wrapper) {
    override fun start() {
        super.start()
        println("Starting Shop Editor Plugin")
    }
}
