import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlin-android-extensions")
    id("kotlinx-serialization")
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
                implementation(Dependencies.Common.Coroutines.Core)
                implementation(Dependencies.Common.Serialization.Json)
                implementation(Dependencies.Common.Ktor.Client.Core)
                implementation(Dependencies.Common.Ktor.Client.commonJson)
                implementation(Dependencies.Common.Ktor.Client.commonLogging)
                implementation(Dependencies.Common.Ktor.Client.commonSerialization)
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
                implementation(Dependencies.Common.Ktor.Client.androidOKHttp)
                implementation(Dependencies.Common.Coroutines.Android)


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
                implementation(Dependencies.Common.Ktor.Client.ios)
            }
        }
        val iosTest by getting
    }
}

android {
    compileSdkVersion(AppConfig.Android.compileSdkVersion)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(AppConfig.Android.minSdkVersion)
        targetSdkVersion(AppConfig.Android.targetSdkVersion)
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