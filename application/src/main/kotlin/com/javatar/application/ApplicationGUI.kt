package com.javatar.application

import com.javatar.api.fs.IFileTypeManager
import com.javatar.api.http.Client
import com.javatar.api.ui.models.AccountModel
import com.javatar.application.plugins.PluginManager
import com.javatar.fs.FileTypeManager
import com.javatar.ui.EditorApplication
import com.javatar.ui.models.CacheConfigurationModel
import com.javatar.ui.models.ClipboardModel
import com.javatar.ui.models.EditorModel
import com.javatar.ui.models.PluginRepositoryModel
import org.apache.log4j.BasicConfigurator
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import tornadofx.DIContainer
import tornadofx.FX
import tornadofx.launch
import kotlin.reflect.KClass

object ApplicationGUI {


    @JvmStatic
    fun main(args: Array<String>) {

        BasicConfigurator.configure()

        startKoin {
            modules(module {
                single { CacheConfigurationModel() }
                single { EditorModel() }
                single { ClipboardModel() }
                single { Client() }
                single { AccountModel() }
                single<IFileTypeManager> { FileTypeManager() }
                single<org.pf4j.PluginManager> { PluginManager }
                single { PluginRepositoryModel() }
            })
        }

        FX.dicontainer = object
            : DIContainer {
            override fun <T : Any> getInstance(type: KClass<T>): T {
                return GlobalContext.get().get(type)
            }
        }

        launch<EditorApplication>()

    }

}
