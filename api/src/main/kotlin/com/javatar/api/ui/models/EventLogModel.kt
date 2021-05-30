package com.javatar.api.ui.models

import com.javatar.api.ui.events.logs.EventLog
import com.javatar.api.ui.events.logs.EventLogBuilder
import com.javatar.api.ui.events.logs.EventLogType
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.scene.paint.Color
import tornadofx.ViewModel
import java.io.File
import java.nio.file.Path
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.io.path.*

class EventLogModel : ViewModel() {

    val logs = bind { SimpleListProperty<EventLog>(this, "logs", FXCollections.observableArrayList()) }

    val latestLog = bind { SimpleObjectProperty<EventLog>(this, "event_log") }

    fun log(block: EventLogBuilder.() -> Unit) = apply {
        logs.add(EventLogBuilder.log(block))
    }

    infix fun xlog(block: EventLogBuilder.() -> Unit) = apply {
        logs.add(EventLogBuilder.log {
            color(Color.RED)
            type(EventLogType.ERROR)
            block(this)
        })
    }

    fun log(msg: String, type: EventLogType) {
        logs.add(EventLogBuilder.log {
            msg(msg)
            type(type)
        })
    }

    infix fun log(msg: String) {
        log(msg, EventLogType.USER_INFORMATION)
    }

    infix fun xlog(msg: String) {
        logs.add(EventLogBuilder.log {
            msg(msg)
            type(EventLogType.ERROR)
            color(Color.RED)
        })
    }

    init {
        logs.addListener(ListChangeListener {
            if (it.next() && it.wasAdded()) {
                if (it.addedSize > 1) {
                    val log = it.addedSubList[0]
                    latestLog.set(log)
                } else {
                    val log = it.addedSubList[it.addedSize - 1]
                    latestLog.set(log)
                }
            }
            writeAndClearLogs()
        })
    }

    fun writeAndClearLogs(force: Boolean = false) {
        if (force || logs.size >= 500) {
            val dir = Path.of("${System.getProperty("user.home")}/rsps-studios/logs")
            if (!dir.exists()) {
                dir.createDirectories()
            }
            if (!dir.isDirectory()) {
                return
            }
            val builder = StringBuilder()
            logs.forEach { log ->
                builder
                    .append(log.timestap)
                    .append(": ")
                    .append(log.logMessage)
                    .append("\n")
            }
            val file = File(
                dir.toFile(), "${
                    DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
                        .format(Calendar.getInstance().toInstant())
                }-logs.txt"
            )
            if (!file.exists()) {
                file.createNewFile()
            }
            file.writeText(builder.toString())
            logs.clear()
        }
    }

    override fun onCommit() {

        with(config) {
            if(logs.isNotEmpty()) {
                config.set("logs" to logs.map { it.displayMessage })
            }
            save()
        }

    }
}