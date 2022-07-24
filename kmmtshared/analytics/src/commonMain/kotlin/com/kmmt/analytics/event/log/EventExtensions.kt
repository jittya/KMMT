package com.kmmt.analytics.event.log

import com.kmmt.analytics.event.log.events.BaseLogEvent

fun BaseLogEvent.logEvent() {
    this.getEvent().logAnalyticsEvent()
}

fun BaseLogEvent.logEvent(data: Map<String, String>) {
    var analyticsEvent = this.getEvent()
    data.forEach {
        analyticsEvent.eventProperties.add(it.key, it.value)
    }
    analyticsEvent.logAnalyticsEvent()
}