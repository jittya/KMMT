plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}

group = AppConfig.group
version = AppConfig.version

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "KMMT shared module. This module is the central module which connect all other kmm submodules"
        homepage = "https://github.com/jittya/KMMT"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iOS_App/Podfile")
        framework {
            baseName = "shared"
            linkerOpts.add("-lsqlite3")
            export(project(Modules.presenter))
            export(project(Modules.core))
            export(project(Modules.common))
            export(project(Modules.injector))
            export(project(Modules.models))
            export(project(Modules.resources))
            export(project(Modules.analytics))
        }
        pod("Mixpanel") {
            version = Versions.Dependencies.iOS.Analytics.mixpanel
        }
        pod("UXCam") {
            version = Versions.Dependencies.iOS.Analytics.uxcam
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(Modules.presenter))
                api(project(Modules.core))
                api(project(Modules.common))
                api(project(Modules.injector))
                api(project(Modules.models))
                api(project(Modules.resources))
                api(project(Modules.analytics))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = AppConfig.Android.compileSdkVersion
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = AppConfig.Android.minSdkVersion
        targetSdk = AppConfig.Android.targetSdkVersion
    }
}