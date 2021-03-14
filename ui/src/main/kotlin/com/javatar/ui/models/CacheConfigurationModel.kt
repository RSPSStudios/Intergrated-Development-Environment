package com.javatar.ui.models

import javafx.beans.property.SimpleMapProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel
import tornadofx.toObservable

class CacheConfigurationModel : ViewModel() {

    val cachePaths = bind {
        SimpleMapProperty(
            this, "cachePaths",
            config.entries.map { it.key as String to it.value as String }.toMap().toObservable()
        )
    }

    val cacheName = bind { SimpleStringProperty(this, "cacheName", "") }

    fun save() {
        with(config) {
            cachePaths.entries.forEach {
                set(it.key to it.value)
            }
            save()
        }
    }

}

