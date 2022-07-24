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
        summary = "KMMT analytics module"
        homepage = "https://github.com/jittya/KMMT"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "analytics"
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
                implementation(Dependencies.KMM.DateTimeKotlin.common)
                implementation(Dependencies.KMM.Koin.Core)
                implementation(project(Modules.common))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependencies.Android.Analvtics.mixpanel)
                implementation(Dependencies.Android.Analvtics.uxcam)
            }
        }
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