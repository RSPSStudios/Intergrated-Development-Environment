package com.javatar.api.ui.notifications

import com.javatar.api.ui.events.logs.EventLog
import com.javatar.api.ui.events.logs.EventLogType
import javafx.geometry.Pos
import org.controlsfx.control.Notifications

class NotificationCenter {

    private var owner: Any = Unit

    fun owner(owner: Any) {
        this.owner = owner
    }

    val ignoreEvents = mutableMapOf(
        EventLogType.USER_INFORMATION to false,
        EventLogType.INFORMATION to false,
        EventLogType.WARNING to false,
        EventLogType.ERROR to false,
        EventLogType.FILTERED to true
    )

    operator fun get(type: EventLogType) : Boolean {
        return ignoreEvents[type] ?: false
    }

    operator fun set(type: EventLogType, ignore: Boolean) {
        ignoreEvents[type] = ignore
    }

    infix fun show(event: EventLog) {
        if (ignoreEvents[event.type] == true) {
            return
        }
        val notification = event.toNotification()
        when (event.type) {
            EventLogType.INFORMATION -> notification.showInformation()
            EventLogType.ERROR -> notification.showError()
            EventLogType.WARNING -> notification.showWarning()
            EventLogType.USER_INFORMATION, EventLogType.FILTERED -> notification.show()
        }
    }

    private fun EventLog.toNotification(): Notifications {
        return Notifications.create()
            .title(
                when (type) {
                    EventLogType.INFORMATION, EventLogType.USER_INFORMATION -> "Information"
                    EventLogType.ERROR -> "Error"
                    EventLogType.WARNING -> "Warning"
                    EventLogType.FILTERED -> "Filtered"
                }
            )
            .text(displayMessage)
            .owner(owner)
            .darkStyle()
            .position(Pos.BOTTOM_RIGHT)
    }

}