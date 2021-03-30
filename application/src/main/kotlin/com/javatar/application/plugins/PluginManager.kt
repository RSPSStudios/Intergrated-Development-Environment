package com.javatar.application.plugins

import org.pf4j.DefaultPluginManager
import org.pf4j.PluginDescriptorFinder

object PluginManager : DefaultPluginManager() {

    override fun getPluginDescriptorFinder(): PluginDescriptorFinder {
        return LinuxManifestPluginDescriptorFinder()
    }
}
