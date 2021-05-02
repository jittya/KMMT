package com.jittyandiyan.shared.core.platform

import com.jittyandiyan.shared.core.platform.expectations.platform

sealed class Platform(val osName: String)
class Android(osName: String,val apiVersion: Int):Platform (osName)
class iOS(osName: String,val osVersion: Double):Platform (osName)

fun runOnAndroid(
    android: Android.() -> Unit
) {
    if (platform is Android) {
        android.invoke(platform)
    }
}

fun runOniOS(
    android: iOS.() -> Unit
) {
    if (platform is iOS) {
        android.invoke(platform)
    }
}