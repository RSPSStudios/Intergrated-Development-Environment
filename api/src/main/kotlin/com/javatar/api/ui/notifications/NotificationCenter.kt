package com.javatar.api.ui.notifications

import com.javatar.api.ui.events.logs.EventLog
import com.javatar.api.ui.events.logs.EventLogType
import javafx.geometry.Pos
import org.controlsfx.control.Notifications
import java.lang.RuntimeException

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

    fun show(event: EventLog) {
        println(event.displayMessage)
        if(ignoreEvents[event.type] == true) {
            return
        }
        val notification = event.toNotification()
        when(event.type) {
            EventLogType.INFORMATION, EventLogType.USER_INFORMATION, EventLogType.FILTERED -> notification.showInformation()
            EventLogType.ERROR -> notification.showError()
            EventLogType.WARNING -> notification.showWarning()
        }
    }

    private fun EventLog.toNotification() : Notifications {
        try {
            return Notifications.create()
                .title(when(type) {
                    EventLogType.INFORMATION -> "Information"
                    EventLogType.ERROR -> "Error"
                    EventLogType.WARNING -> "Warning"
                    EventLogType.USER_INFORMATION -> "Information"
                    EventLogType.FILTERED -> "Filtered"
                })
                .text(displayMessage)
                .owner(owner)
                .darkStyle()
                .position(Pos.BOTTOM_RIGHT)
        } catch (e: Exception) {
            println(displayMessage)
            println(type.name)
            e.printStackTrace()
            throw RuntimeException()
        }
    }

}