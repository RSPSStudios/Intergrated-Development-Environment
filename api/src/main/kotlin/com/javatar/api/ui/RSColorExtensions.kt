package com.javatar.api.ui

import javafx.scene.paint.Color
import kotlin.math.roundToInt

fun Color.toInt(): Int {
    val r = (red * 255).roundToInt()
    val g = (green * 255).roundToInt()
    val b = (blue * 255).roundToInt()
    return r shl 16 or (g shl 8) or b
}

fun Int.toFXColor(): Color {
    val r = this ushr 16 and 0xFF
    val g = this ushr 8 and 0xFF
    val b = this and 0xFF
    return Color.rgb(r, g, b)
}