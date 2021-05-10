package com.javatar.plugin.definition.editor.ui

import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.control.SpinnerValueFactory
import javafx.util.StringConverter

class NumberSpinnerValueFactory(min: Int = 0, max: Int = Int.MAX_VALUE) : SpinnerValueFactory<Number>() {
    val minProperty = SimpleIntegerProperty(min)
    val maxProperty = SimpleIntegerProperty(max)

    init {
        converter = NumberStringConverter()
    }

    override fun decrement(steps: Int) {
        val value = value
        if ((value.toInt() - steps) < minProperty.get()) {
            valueProperty().set(minProperty.get())
        } else {
            valueProperty().set(value.toInt() - steps)
        }
    }

    override fun increment(steps: Int) {
        val value = value
        if ((value.toInt() + steps) > maxProperty.get()) {
            valueProperty().set(maxProperty.get())
        } else {
            valueProperty().set(value.toInt() + steps)
        }
    }

    inner class NumberStringConverter : StringConverter<Number>() {
        override fun toString(num: Number): String {
            return num.toString()
        }

        override fun fromString(string: String?): Number {
            return string?.toInt() ?: 0
        }
    }
}