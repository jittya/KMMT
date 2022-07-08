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
include(":presenter")
include(":core")
include(":persistence")
include(":injector")
include(":common")
include(":network")
include(":models")
include(":domain")
