plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("kotlinx-serialization")
    id("com.squareup.sqldelight")
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
            linkerOpts.add("-lsqlite3")
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.KMM.Klock.common)
                implementation(Dependencies.KMM.Coroutines.Core)
                implementation(Dependencies.KMM.Serialization.Json)
                implementation(Dependencies.KMM.Ktor.Client.Core)
                implementation(Dependencies.KMM.Ktor.Client.commonLogging)
                implementation(Dependencies.KMM.Ktor.Client.commonSerialization)
                implementation(Dependencies.KMM.Ktor.Client.contentNegotiation)
                implementation(Dependencies.KMM.SQLDelight.Runtime)
                implementation(Dependencies.KMM.Koin.Core)
                implementation(Dependencies.KMM.Settings.common)
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
                implementation(Dependencies.KMM.SQLDelight.AndroidDriver)
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
                implementation(Dependencies.KMM.SQLDelight.NativeDriver)
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

sqldelight {
    database(AppConfig.dbName) {
        packageName = AppConfig.group
    }
    linkSqlite = true
}