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