package com.kmmt.persistance.keyValueStore.settings.expectations

import com.russhwolf.settings.AppleSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults

actual val settings: Settings
    get() = AppleSettings(NSUserDefaults.standardUserDefaults())