package com.kmmt.analytics.event.log.events

import kotlinx.datetime.*


sealed class BaseGeneralEvent : BaseLogEvent(
    eventType = "General",
)

class EventAppOpened(): BaseGeneralEvent()
{
    override fun properties(): Map<String, String> {
        return emptyMap()
    }

    override fun getEventDesc(): String {
        return "KMMT App Launched"
    }

}

// Duration Events
sealed class BaseEventDuration : BaseGeneralEvent() {
    private var startTime: LocalDateTime? = null
    private var endTime: LocalDateTime? = null

    fun start(): BaseEventDuration {
        startTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        return this
    }

    fun stop(): BaseEventDuration {
        endTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        return this
    }

    private fun getTimeTaken(): Long {
        return if (startTime != null && endTime != null) {
            (endTime!!.toInstant(TimeZone.currentSystemDefault())
                .minus(startTime!!.toInstant(TimeZone.currentSystemDefault()))).inWholeSeconds
        } else {
            0
        }
    }

    override fun properties(): Map<String, String> {
        return mapOf("TimeTakenInSeconds" to getTimeTaken().toString())
    }

}

class EventScreenDuration(private val screenName: ScreenName) : BaseEventDuration() {
    override fun getEventDesc(): String {
        return "Screen Duration"
    }

    override fun properties(): Map<String, String> {
        return super.properties().toMutableMap().plus("ScreenName" to screenName.name)
    }
}

enum class ScreenName {
    Welcome, Dashboard, ActivityOverview, ProgramSelection, Settings, Training, UserSelection, WorkoutSummary
}

