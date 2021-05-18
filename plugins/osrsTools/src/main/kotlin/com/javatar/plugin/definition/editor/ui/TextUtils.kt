package com.javatar.plugin.definition.editor.ui

import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.scene.text.TextBoundsType
import kotlin.math.ceil

object TextUtils {
    val helper: Text = Text()
    const val DEFAULT_WRAPPING_WIDTH = 0.0
    const val DEFAULT_LINE_SPACING = 0.0
    val DEFAULT_TEXT: String? = null
    val DEFAULT_BOUNDS_TYPE: TextBoundsType? = null
    fun computeTextWidth(font: Font, text: String, help0: Double = 0.0): Double {
        helper.text = text
        helper.font = font
        helper.wrappingWidth = 0.0
        helper.lineSpacing = 0.0
        var d = helper.prefWidth(-1.0).coerceAtMost(help0)
        helper.wrappingWidth = ceil(d)
        d = ceil(helper.layoutBounds.width)
        helper.wrappingWidth = DEFAULT_WRAPPING_WIDTH
        helper.lineSpacing = DEFAULT_LINE_SPACING
        helper.text = DEFAULT_TEXT
        return d
    }
}