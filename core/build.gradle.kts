plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("kotlinx-serialization")
}

group = AppConfig.group
version = AppConfig.version

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "KMMT Core module"
        homepage = "https://github.com/jittya/KMMT"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "core"
            export(project(":persistence"))
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":persistence"))
                implementation(project(":common"))
                implementation(Dependencies.KMM.Klock.common)
                implementation(Dependencies.KMM.Ktor.Client.Core)
                implementation(Dependencies.KMM.Ktor.Client.commonLogging)
                implementation(Dependencies.KMM.Ktor.Client.commonSerialization)
                implementation(Dependencies.KMM.Ktor.Client.contentNegotiation)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependencies.Android.google_android_material_Material)
                implementation(Dependencies.KMM.Ktor.Client.androidOKHttp)
                implementation(Dependencies.KMM.Coroutines.Android)
                implementation(Dependencies.KMM.Koin.Android)
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
            dependencies {
                implementation(Dependencies.KMM.Ktor.Client.ios)
            }
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
    buildFeatures {
        viewBinding = true
    }
    compileSdk = AppConfig.Android.compileSdkVersion
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = AppConfig.Android.minSdkVersion
        targetSdk = AppConfig.Android.targetSdkVersion
    }
}