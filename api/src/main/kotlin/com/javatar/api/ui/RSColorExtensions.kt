package com.javatar.api.ui

import javafx.scene.paint.Color
import java.awt.Color.HSBtoRGB
import java.awt.Color.RGBtoHSB
import kotlin.math.roundToInt

fun Color.toInt(): Int {
    val a = (opacity * 255).roundToInt()
    val r = (red * 255).roundToInt()
    val g = (green * 255).roundToInt()
    val b = (blue * 255).roundToInt()
    return a shl 24 or r shl 16 or (g shl 8) or b
}

fun Int.toFXColor(): Color {
    val a = this ushr 24 and 0xFF
    val r = this ushr 16 and 0xFF
    val g = this ushr 8 and 0xFF
    val b = this and 0xFF
    return Color.rgb(r, g, b, a.toDouble() / 255)
}

fun Color.toRS2() : Int {
    val r = (red * 255).roundToInt()
    val g = (green * 255).roundToInt()
    val b = (blue * 255).roundToInt()
    return RGB_to_RS2HSB(r, g, b)
}

fun Int.toColor() : Color {
    return RS2HSB_to_RGB(this).toFXColor()
}

private fun RGB_to_RS2HSB(red: Int, green: Int, blue: Int): Int {
    val HSB: FloatArray = RGBtoHSB(red, green, blue, null)
    val hue = HSB[0]
    val saturation = HSB[1]
    val brightness = HSB[2]
    val encode_hue = (hue * 63).toInt() //to 6-bits
    val encode_saturation = (saturation * 7).toInt() //to 3-bits
    val encode_brightness = (brightness * 127).toInt() //to 7-bits
    return (encode_hue shl 10) + (encode_saturation shl 7) + encode_brightness
}

private fun RS2HSB_to_RGB(RS2HSB: Int): Int {
    val decode_hue = RS2HSB shr 10 and 0x3f
    val decode_saturation = RS2HSB shr 7 and 0x07
    val decode_brightness = RS2HSB and 0x7f
    return HSBtoRGB(decode_hue.toFloat() / 63, decode_saturation.toFloat() / 7, decode_brightness.toFloat() / 127)
}