plugins {
    id("com.android.application")
    kotlin("android")
}

group = AppConfig.group
version = AppConfig.version

dependencies {
    implementation(project(":shared"))
    implementation("org.conscrypt:conscrypt-android:2.5.2")
    implementation(Dependencies.Android.google_android_material_Material)
    implementation(Dependencies.Android.androidx_appcompat_Appcompat)
    implementation(Dependencies.Android.androidx_constraintlayout_Constraintlayout)
    implementation(Dependencies.KMM.Koin.Core)
    implementation(Dependencies.KMM.Koin.Android)
}

android {
    buildFeatures {
        viewBinding = true
    }
    compileSdkVersion(AppConfig.Android.compileSdkVersion)
    defaultConfig {
        applicationId = AppConfig.Android.packageName
        minSdkVersion(AppConfig.Android.minSdkVersion)
        targetSdkVersion(AppConfig.Android.targetSdkVersion)
        versionCode = AppConfig.versionCode
        versionName = AppConfig.version
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = false
            resValue("string", "app_name", AppConfig.AppName)
        }
        getByName("debug") {
            applicationIdSuffix = ".$name"
            versionNameSuffix = ".$name"
            isDebuggable = true
            resValue("string", "app_name", AppConfig.AppName + " $name")
        }
    }
    flavorDimensions("version")
    productFlavors {
        create("dev") {
            dimension("version")
            applicationIdSuffix = ".$name"
            versionNameSuffix = "-$name"
            buildConfigField("String", "Server", "\"0.0.0.0\"")
            buildConfigField("String", "Port", "\"8080\"")
        }
        create("prod") {
            dimension("version")
            applicationIdSuffix = ".$name"
            versionNameSuffix = "-$name"
            buildConfigField("String", "Server", "\"0.0.0.0\"")
            buildConfigField("String", "Port", "\"8080\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}