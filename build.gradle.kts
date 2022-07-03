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
    }
}

group = AppConfig.group
version = AppConfig.version

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}