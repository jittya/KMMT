package com.kmmt.analytics.event.log.events


import com.kmmt.analytics.core.AnalyticsEvent
import com.kmmt.analytics.core.hash256
import com.kmmt.analytics.event.log.LogCategory
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

sealed class BaseLogEvent(
    val eventType: String,
    var eventCategory: LogCategory = LogCategory.Information,
    val eventTime: String = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        .toString()
) {

    abstract fun properties(): Map<String, String>

    fun getEvent(): AnalyticsEvent {
        return defaultEvent().add(properties = properties())
    }

    abstract fun getEventDesc(): String

    private fun defaultEvent(): AnalyticsEvent {
        return AnalyticsEvent(
            eventType,
            eventLog = this,
            eventDesc = getEventDesc(),
            eventTime = eventTime,
            eventCategory = eventCategory
        )
    }

    open fun getHashID(): String {
        return getEventDesc().hash256()
    }

}