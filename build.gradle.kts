buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Plugins.kotlinGradlePlugin)
        classpath(Plugins.AndroidBuildGradlePlugin)
        classpath(Plugins.kotlinSerializationGradlePlugin)
        classpath(Plugins.SQLDelightGradlePlugin)
        classpath(Plugins.RealmGradlePlugin)
        classpath(Plugins.MokoResourceGenerator)
    }
}

group = AppConfig.group
version = AppConfig.version

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://sdk.uxcam.com/android/")
    }
}