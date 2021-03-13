package com.javatar.application.plugins

import org.pf4j.DefaultPluginManager
import org.pf4j.RuntimeMode

object PluginManager : DefaultPluginManager() {
    override fun getRuntimeMode(): RuntimeMode {
        return RuntimeMode.DEPLOYMENT
    }
}
