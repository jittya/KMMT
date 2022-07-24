package com.kmmt.analytics.core


class AnalyticsEventProperties {
    private var properties = mutableMapOf<String, String>()

    fun add(key: String, value: String): AnalyticsEventProperties {
        properties[key] = value
        return this
    }

    fun getProperties(): MutableMap<String, String> {
        return properties
    }
}



