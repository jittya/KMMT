package com.kmmt.analytics.platforms.uxcam

import com.kmmt.analytics.core.AnalyticsEvent
import com.kmmt.analytics.expectations.AnalyticsContext

expect class AnalyticsUXCam(analyticsContext: AnalyticsContext) {
    fun track(analyticsEvent: AnalyticsEvent)
}
