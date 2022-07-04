package com.kmmt.core.platform

import com.kmmt.core.platform.expectations.platform

sealed class Platform(val osName: String)
class AndroidPlatform(osName: String, val apiVersion: Int):Platform (osName)
class IOSPlatform(osName: String, val osVersion: Double):Platform (osName)

fun runOnAndroid(
    androidPlatform: AndroidPlatform.() -> Unit
) {
    if (platform is AndroidPlatform) {
        androidPlatform.invoke(platform)
    }
}

fun runOniOS(
    android: IOSPlatform.() -> Unit
) {
    if (platform is IOSPlatform) {
        android.invoke(platform)
    }
}