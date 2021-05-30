package com.javatar.ui.models

import com.javatar.api.ui.events.logs.EventLogType
import com.javatar.api.ui.notifications.NotificationCenter
import javafx.beans.property.SimpleBooleanProperty
import tornadofx.ViewModel
import tornadofx.onChange

class GeneralPreferencesModel : ViewModel() {

    val ignoreUserEvents = bind { SimpleBooleanProperty(this, "ignore_user_events", config.boolean("hide_user_events", false)) }
    val ignoreInformationEvents = bind { SimpleBooleanProperty(this, "ignore_information_events", config.boolean("hide_info_events", false)) }
    val ignoreWarningEvents = bind { SimpleBooleanProperty(this, "ignore_warning_events", config.boolean("hide_warning_events", false)) }
    val ignoreErrorEvents = bind { SimpleBooleanProperty(this, "ignore_error_events", config.boolean("hide_error_events", false)) }
    val ignoreFilteredEvents = bind { SimpleBooleanProperty(this, "ignore_filtered_events", config.boolean("hide_filtered_events", true)) }

    val notificationCenter: NotificationCenter by di()

    init {
        ignoreUserEvents.onChange {
            notificationCenter[EventLogType.USER_INFORMATION] = it
            onCommit()
        }
        ignoreInformationEvents.onChange {
            notificationCenter[EventLogType.INFORMATION] = it
            onCommit()
        }
        ignoreWarningEvents.onChange {
            notificationCenter[EventLogType.WARNING] = it
            onCommit()
        }
        ignoreErrorEvents.onChange {
            notificationCenter[EventLogType.ERROR] = it
            onCommit()
        }
        ignoreFilteredEvents.onChange {
            notificationCenter[EventLogType.FILTERED] = it
            onCommit()
        }
    }

    override fun onCommit() {
        with(config) {
            set("hide_user_events", ignoreUserEvents.get())
            set("hide_info_events", ignoreInformationEvents.get())
            set("hide_warning_events", ignoreWarningEvents.get())
            set("hide_error_events", ignoreErrorEvents.get())
            set("hide_filtered_events", ignoreFilteredEvents.get())
            save()
        }
    }
}