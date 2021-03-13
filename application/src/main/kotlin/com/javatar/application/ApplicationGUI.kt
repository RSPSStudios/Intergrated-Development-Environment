package com.javatar.application

import com.javatar.application.plugins.PluginManager
import com.javatar.ui.EditorApplication
import com.javatar.ui.data.PluginInformation
import com.javatar.ui.models.ActiveDirectoryModel
import com.javatar.ui.models.CacheConfigurationModel
import com.javatar.ui.models.EditorModel
import com.javatar.ui.models.PluginRepositoryModel
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.pf4j.JarPluginManager
import org.pf4j.PluginState
import tornadofx.DIContainer
import tornadofx.FX
import tornadofx.getInstance
import tornadofx.launch
import java.nio.file.Files
import java.nio.file.Path
import kotlin.reflect.KClass

object ApplicationGUI {

    @JvmStatic
    fun main(args: Array<String>) {

        startKoin {
            modules(module {
                single { CacheConfigurationModel() }
                single { ActiveDirectoryModel() }
                single { EditorModel() }
                single { PluginRepositoryModel() }
                single<JarPluginManager> { PluginManager }
            })
        }

        FX.dicontainer = object
            : DIContainer {
            override fun <T : Any> getInstance(type: KClass<T>): T {
                return GlobalContext.get().get(type)
            }
        }

        System.setProperty("pf4j.pluginsDir", "${System.getProperty("user.home")}/plugins")

        if (!Files.exists(Path.of(System.getProperty("pf4j.pluginsDir")))) {
            Files.createDirectory(Path.of(System.getProperty("pf4j.pluginsDir")))
        }

        PluginManager.loadPlugins()
        PluginManager.startPlugins()

        PluginManager.plugins.forEach {
            val model = (FX.dicontainer!!).getInstance<PluginRepositoryModel>()
            model.plugins.add(
                PluginInformation(
                    it.descriptor.pluginId,
                    it.descriptor.provider,
                    it.descriptor.pluginDescription,
                    it.descriptor.version,
                    it.pluginState == PluginState.STARTED
                )
            )
        }

        launch<EditorApplication>()

    }

}
