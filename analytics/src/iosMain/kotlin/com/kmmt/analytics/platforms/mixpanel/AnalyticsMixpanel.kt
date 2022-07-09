package com.kmmt.analytics.platforms.mixpanel

import cocoapods.Mixpanel.Mixpanel
import com.kmmt.analytics.core.AnalyticsEvent
import com.kmmt.analytics.expectations.AnalyticsContext

actual class AnalyticsMixpanel actual constructor(analyticsContext: AnalyticsContext) {

    init {
        Mixpanel.sharedInstanceWithToken(analyticsContext.apiToken)
    }

    actual fun track(analyticsEvent: AnalyticsEvent) {
        Mixpanel.sharedInstance()?.track(
            analyticsEvent.eventDesc,
            analyticsEvent.eventProperties.getProperties().toMap()
        )
    }

    actual fun track(eventName:String,  properties:Map<String, String>)
    {
        Mixpanel.sharedInstance()?.track(eventName,properties.toMap())
    }

    actual fun reset() {
        Mixpanel.sharedInstance()?.reset()
    }

    actual fun setPeopleProperties(properties: Map<String, String>)
    {
        Mixpanel.sharedInstance()?.people?.set(properties.toMap())
    }

    fun getMixpanel(): Mixpanel? {
        return Mixpanel.sharedInstance()
    }
}
