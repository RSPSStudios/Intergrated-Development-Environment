package com.javatar.ui.models

import com.displee.cache.CacheLibrary
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.Commit
import tornadofx.ItemViewModel

class CacheConfig(val customCache: String, val osrsCache: String)

class CacheConfigurationModel : ItemViewModel<CacheConfig>() {

    val CUSTOM_CACHE_KEY = "custom"
    val OSRS_CACHE_KEY = "osrs"

    val customCachePath = bind { SimpleStringProperty(this, "customCache", config.string(CUSTOM_CACHE_KEY)) }
    val osrsCachePath = bind { SimpleStringProperty(this, "osrsCache", config.string(OSRS_CACHE_KEY)) }

    val customCache = SimpleObjectProperty<CacheLibrary>().also {
        it.bind(Bindings.createObjectBinding({
            if (customCachePath.get() != null) {
                CacheLibrary.create(customCachePath.get())
            } else null
        }, customCachePath))
    }
    val osrsCache = SimpleObjectProperty<CacheLibrary>().also {
        it.bind(Bindings.createObjectBinding({
            if (osrsCachePath.get() != null) {
                CacheLibrary.create(osrsCachePath.get())
            } else null
        }, osrsCachePath))
    }

    override fun onCommit() {
        with(config) {
            set(CUSTOM_CACHE_KEY to customCachePath.value)
            set(OSRS_CACHE_KEY to osrsCachePath.value)
            save()
        }
    }

    override fun onCommit(commits: List<Commit>) {
        commits.forEach {
            if (it.changed && it.property == customCachePath) {
                customCache.set(CacheLibrary.create(customCachePath.get()))
            } else if (it.changed && it.property == osrsCachePath) {
                osrsCache.set(CacheLibrary.create(osrsCachePath.get()))
            }
        }
    }
}

