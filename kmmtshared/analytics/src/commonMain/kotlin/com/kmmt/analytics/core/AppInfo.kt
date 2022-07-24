package com.kmmt.analytics.core


data class AppInfo(
    val deviceID: String,
    val deviceName: String,
    val deviceModel: String,
    val IP: String,
    val OS: String,
    val OSVersion: String,
    val appID: String,
    val appVersion: String,
    val appVersionCode: String,
    val appBuildType: BuildType,
    val appBuildFlavor: String
) {
    fun isRelease(): Boolean {
        return appBuildType == BuildType.RELEASE
    }
}

enum class BuildType(buildType:String){
    DEBUG("debug"),
    RELEASE("release")
}