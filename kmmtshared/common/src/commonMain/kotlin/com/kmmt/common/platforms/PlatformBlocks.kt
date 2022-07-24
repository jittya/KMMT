package com.kmmt.common.platforms

import com.kmmt.common.expectations.platform


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