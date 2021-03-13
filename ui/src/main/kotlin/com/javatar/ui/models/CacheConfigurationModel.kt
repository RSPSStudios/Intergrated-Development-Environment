package com.javatar.ui.models

import com.displee.cache.CacheLibrary
import javafx.beans.property.SimpleMapProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel
import tornadofx.toObservable

class CacheConfig(val customCache: String, val osrsCache: String)

class CacheConfigurationModel : ItemViewModel<CacheConfig>() {

    val cachePaths = bind {
        SimpleMapProperty(
            this, "cachePaths",
            config.filterNot { it.key as String == "activeCachePath" }
                .map { entry -> entry.key as String to entry.value as String }.toMap().toObservable()
        )
    }

    val activeCachePath = bind { SimpleStringProperty(this, "activeCachePath", config.string("activeCache")) }

    val activeCache = bind { SimpleObjectProperty<CacheLibrary>(this, "activeCache") }

    val cacheName = bind { SimpleStringProperty(this, "cacheName", "") }

    init {
        if (activeCachePath.get() != null) {
            activeCache.set(CacheLibrary.create(activeCachePath.get()))
        }
    }

    override fun onCommit() {
        with(config) {
            set("activeCachePath", activeCachePath.get())
            cachePaths.forEach { name, path ->
                if (name != null && path != null) {
                    set(name, path)
                }
            }
            save()
        }
    }
}

