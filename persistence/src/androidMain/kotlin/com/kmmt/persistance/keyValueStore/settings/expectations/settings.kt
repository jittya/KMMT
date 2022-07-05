package com.kmmt.persistance.keyValueStore.settings.expectations

import android.content.Context
import com.kmmt.common.dependencyInjection.Android
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings

actual val settings: Settings
    get() = AndroidSettings(Android.application.application.getSharedPreferences("KeyValueStore", Context.MODE_PRIVATE))