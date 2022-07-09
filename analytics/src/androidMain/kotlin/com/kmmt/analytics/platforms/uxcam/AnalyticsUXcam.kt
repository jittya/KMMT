package com.kmmt.analytics.platforms.uxcam

import com.kmmt.analytics.core.AnalyticsEvent
import com.kmmt.analytics.expectations.AnalyticsContext
import com.kmmt.analytics.platforms.mixpanel.AnalyticsMixpanel
import com.uxcam.OnVerificationListener
import com.uxcam.UXCam
import com.uxcam.datamodel.UXConfig
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


actual class AnalyticsUXCam actual constructor(analyticsContext: AnalyticsContext) : KoinComponent {
    private val mixpanel: AnalyticsMixpanel by inject()

    init {
        val config = UXConfig.Builder(analyticsContext.apiToken)
            .enableAutomaticScreenNameTagging(true)
            .enableImprovedScreenCapture(true)
            .build()
        UXCam.startWithConfiguration(config)
        UXCam.addVerificationListener(object : OnVerificationListener {
            override fun onVerificationSuccess() {
                UXCam.urlForCurrentSession()?.let { sessionURL ->
                    mixpanel.track(
                        "UXCam: Session Recording link",
                        mapOf("session_url" to sessionURL)
                    )
                }
                UXCam.urlForCurrentUser()?.let { userURL ->
                    mixpanel.setPeopleProperties(mapOf("uxcam_user_url" to userURL))
                }
            }

            override fun onVerificationFailed(p0: String?) {
            }
        })
    }

    actual fun track(analyticsEvent: AnalyticsEvent) {
        UXCam.logEvent(
            analyticsEvent.eventDesc,
            analyticsEvent.eventProperties.getProperties().toMap()
        )
    }

}
