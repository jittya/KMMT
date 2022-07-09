package com.kmmt.analytics.platforms.mixpanel

import com.kmmt.analytics.core.AnalyticsEvent
import com.kmmt.analytics.expectations.AnalyticsContext

expect class AnalyticsMixpanel(analyticsContext: AnalyticsContext) {
    fun track(analyticsEvent: AnalyticsEvent)
    fun track(eventName:String,  properties:Map<String, String>)
    fun reset()
    fun setPeopleProperties(properties: Map<String, String>)
}
