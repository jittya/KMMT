package com.kmmt.core.serialization

import kotlinx.serialization.json.Json

class JsonSerializationHelper {
    companion object {
        fun JsonX(): Json {
            return Json {
                ignoreUnknownKeys = true
                isLenient = true
                allowSpecialFloatingPointValues = true
                encodeDefaults = true
            }
        }
    }
}