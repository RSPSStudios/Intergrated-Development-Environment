package com.javatar.api.ui.utilities

import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import tornadofx.getChildList
import tornadofx.onChange

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 16 2021
 */

fun <S : EventTarget> S.dynamicContent(vararg deps: ObservableValue<*>, onChangeBuilder: S.() -> Unit) {
    val onChange = {
        getChildList()?.clear()
        onChangeBuilder(this@dynamicContent)
    }
    deps.forEach { it.onChange { onChange() } }
    onChange()
}
