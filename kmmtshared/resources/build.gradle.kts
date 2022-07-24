plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("dev.icerock.mobile.multiplatform-resources")
}

group = AppConfig.group
version = AppConfig.version

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "KMMT resource module"
        homepage = "https://github.com/jittya/KMMT"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "resources"
            export(Dependencies.KMM.MOKO.resources)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(Dependencies.KMM.MOKO.resources)
                api(project(Modules.common))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api(Dependencies.KMM.MOKO.resources_compose)
                implementation(Dependencies.Android.Compose.ui)
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

multiplatformResources {
    multiplatformResourcesPackage = "com.kmmt.resources" // required
    multiplatformResourcesClassName = "Resources" // optional, default MR
}