package com.kmmt.analytics.core

import com.kmmt.analytics.event.log.LogCategory
import com.kmmt.analytics.event.log.events.BaseLogEvent
import com.kmmt.analytics.platforms.mixpanel.AnalyticsMixpanel
import com.kmmt.analytics.platforms.uxcam.AnalyticsUXCam
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


data class AnalyticsEvent(
    var eventType: String,
    val eventProperties: AnalyticsEventProperties = AnalyticsEventProperties(),
    val eventTime: String = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString(),
    var eventDesc: String,
    val eventLog: BaseLogEvent,
    var eventCategory: LogCategory
) : KoinComponent {

    val appInfo: AppInfo by inject()
    val mixpanel: AnalyticsMixpanel by inject()
    val uxCam: AnalyticsUXCam by inject()

    fun add(key: String, value: String): AnalyticsEvent {
        eventProperties.getProperties()[key] = value
        return this
    }

    fun add(properties: Map<String, String>): AnalyticsEvent {
        eventProperties.getProperties().putAll(properties)
        return this
    }

    fun logAnalyticsEvent() {
        println("logAnalyticsEvent > $this")
        if (appInfo.isRelease()) {
            mixpanel.track(this)
            uxCam.track(this)
        }
    }
}

