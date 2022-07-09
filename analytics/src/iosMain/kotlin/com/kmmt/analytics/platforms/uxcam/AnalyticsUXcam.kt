package com.kmmt.analytics.platforms.uxcam

import cocoapods.UXCam.UXCam
import cocoapods.UXCam.UXCamConfiguration
import com.kmmt.analytics.core.AnalyticsEvent
import com.kmmt.analytics.expectations.AnalyticsContext
import com.kmmt.analytics.platforms.mixpanel.AnalyticsMixpanel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class AnalyticsUXCam actual constructor(analyticsContext: AnalyticsContext):KoinComponent {
    private val mixpanel: AnalyticsMixpanel by inject()
    init {
        val configuration = UXCamConfiguration(appKey = analyticsContext.apiToken)
        UXCam.optIntoSchematicRecordings()
        UXCam.startWithConfiguration(configuration){ status ->
            if (status)
            {
                UXCam.urlForCurrentSession()?.let { sessionURL->
                    mixpanel.getMixpanel()?.track("UXCam: Session Recording link", mapOf("session_url" to sessionURL))
                }
                UXCam.urlForCurrentUser()?.let { userURL->
                    mixpanel.setPeopleProperties( mapOf("uxcam_user_url" to userURL))
                }
            }
        }
    }

    actual fun track(analyticsEvent: AnalyticsEvent) {
        UXCam.logEvent(
            analyticsEvent.eventDesc,
            analyticsEvent.eventProperties.getProperties().toMap()
        )
    }

}
