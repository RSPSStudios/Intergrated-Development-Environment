package com.javatar.api.ui.events.logs

import javafx.scene.paint.Color
import javafx.scene.text.Font

class EventLogBuilder {

    private var message: String = ""
    private var color: Color = Color.WHITE
    private var font: Font = Font.font(14.0)
    private var logMessage: String = ""
    private var type: EventLogType = EventLogType.USER_INFORMATION

    infix fun msg(msg: String) = apply {
        message = msg
    }

    infix fun logMsg(msg: String) = apply {
        logMessage = msg
    }

    infix fun color(color: Color) = apply {
        this.color = color
    }

    infix fun font(font: Font) = apply {
        this.font = font
    }

    infix fun type(type: EventLogType) = apply {
        this.type = type
    }

    fun build() = EventLog(message, logMessage, color, font, type)

    companion object {
        fun log(block: EventLogBuilder.() -> Unit) : EventLog {
            val builder = EventLogBuilder()
            block(builder)
            return builder.build()
        }
    }

}