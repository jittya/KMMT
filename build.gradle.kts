buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Plugins.kotlinGradlePlugin)
        classpath(Plugins.AndroidBuildGradlePlugin)
        classpath(Plugins.kotlinSerializationGradlePlugin)
    }
}

group = AppConfig.group
version = AppConfig.version

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}