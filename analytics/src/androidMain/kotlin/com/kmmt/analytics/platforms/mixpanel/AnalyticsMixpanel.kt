package com.kmmt.analytics.platforms.mixpanel

import android.content.Context
import com.mixpanel.android.mpmetrics.MixpanelAPI
import com.kmmt.analytics.core.AnalyticsEvent
import com.kmmt.analytics.expectations.AnalyticsContext
import org.json.JSONObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.get


actual class AnalyticsMixpanel actual constructor(analyticsContext: AnalyticsContext) :
    KoinComponent {

    private var apiKey: String

    init {
        MixpanelAPI.getInstance(analyticsContext.context, analyticsContext.apiToken)
        this.apiKey = analyticsContext.apiToken
    }

    fun getMixpanel(): MixpanelAPI? {
        return MixpanelAPI.getInstance(get() as Context, apiKey)
    }

    actual fun track(analyticsEvent: AnalyticsEvent) {
        getMixpanel()?.track(
            analyticsEvent.eventDesc,
            analyticsEvent.eventProperties.getProperties().toJsonObject()
        )
    }


    actual fun reset() {
        getMixpanel()?.reset()
    }

    actual fun track(eventName: String, properties: Map<String, String>) {
        getMixpanel()?.track(eventName, properties.toJsonObject())
    }

    actual fun setPeopleProperties(properties: Map<String, String>) {
        getMixpanel()?.people?.set(properties.toJsonObject())
    }

    private fun Map<String, String>.toJsonObject(): JSONObject {
        return JSONObject().apply {
            this@toJsonObject.forEach { property ->
                (property.key as? String)?.let { put(it, property.value) }
            }
        }
    }
}
