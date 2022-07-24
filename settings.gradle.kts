pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    
}
rootProject.name = "KMMT"


include(":androidApp")
include(":kmmtshared:presenter")
include(":kmmtshared:core")
include(":kmmtshared:persistence")
include(":kmmtshared:injector")
include(":kmmtshared:common")
include(":kmmtshared:network")
include(":kmmtshared:models")
include(":kmmtshared:domain")
include(":kmmtshared:resources")
include(":kmmtshared:analytics")
include(":kmmtshared")
