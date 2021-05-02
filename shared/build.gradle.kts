import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlinx-serialization")
    id("com.squareup.sqldelight")
}

group = AppConfig.group
version = AppConfig.version

kotlin {
    android()
    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.KMM.Klock.common)
                implementation(Dependencies.KMM.Coroutines.Core)
                implementation(Dependencies.KMM.Serialization.Json)
                implementation(Dependencies.KMM.Ktor.Client.Core)
                implementation(Dependencies.KMM.Ktor.Client.commonJson)
                implementation(Dependencies.KMM.Ktor.Client.commonLogging)
                implementation(Dependencies.KMM.Ktor.Client.commonSerialization)
                implementation(Dependencies.KMM.SQLDelight.Runtime)
                implementation(Dependencies.KMM.Koin.Core)
                implementation(Dependencies.KMM.Settings.common)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
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
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(Dependencies.Android.junit_Junit)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(Dependencies.KMM.Ktor.Client.ios)
                implementation(Dependencies.KMM.SQLDelight.NativeDriver)
            }
        }
        val iosTest by getting
    }
}

android {
    buildFeatures {
        viewBinding = true
    }
    compileSdkVersion(AppConfig.Android.compileSdkVersion)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(AppConfig.Android.minSdkVersion)
        targetSdkVersion(AppConfig.Android.targetSdkVersion)
    }
}

sqldelight {
    database(AppConfig.dbName) {
        packageName = AppConfig.group
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}

tasks.getByName("build").dependsOn(packForXcode)

