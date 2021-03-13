package com.javatar.application

import com.javatar.ui.EditorApplication
import com.javatar.ui.models.ActiveDirectoryModel
import com.javatar.ui.models.CacheConfigurationModel
import com.javatar.ui.models.EditorModel
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

        startKoin {
            modules(module {
                single { CacheConfigurationModel() }
                single { ActiveDirectoryModel() }
                single { EditorModel() }
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
