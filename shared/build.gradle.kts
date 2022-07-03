
plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlinx-serialization")
    kotlin("native.cocoapods")
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
            export(project(":core"))
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":core"))
                implementation(Dependencies.KMM.Coroutines.Core)
                implementation(Dependencies.KMM.Koin.Core)
                implementation(Dependencies.KMM.Ktor.Client.Core)
                implementation(Dependencies.KMM.Serialization.Json)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(Dependencies.Android.junit_Junit)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependencies {
            }
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