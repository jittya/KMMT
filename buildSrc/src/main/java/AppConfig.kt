object AppConfig {
    const val AppName = "KMMT App"
    const val versionCode = 1
    const val version = "0.1.1.21.4.26" //Major.Minor.intermediate.Year.Month.Day
    const val group = "com.jittyandiyan.mobile"

    object Android {
        var appName = AppName.replace(" ", "")
        var packageName = "$group.$appName.android"
        const val compileSdkVersion = 29
        const val minSdkVersion = 21
        const val targetSdkVersion = 29
    }
}



