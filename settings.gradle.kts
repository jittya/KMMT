pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
    }
    
}
rootProject.name = "KMMT"


include(":androidApp")
include(":shared")
include(":core")
include(":persistence")
