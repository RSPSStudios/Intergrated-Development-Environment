package com.javatar.api.ui.events.logs

import javafx.scene.paint.Color
import javafx.scene.text.Font
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EventLog(val displayMessage: String, val logMessage: String, val color: Color = Color.WHITE, val font: Font = Font.font(14.0), val type: EventLogType = EventLogType.USER_INFORMATION) {
    val timestap = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}