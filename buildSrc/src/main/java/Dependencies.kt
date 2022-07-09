import org.gradle.kotlin.dsl.provideDelegate

object Dependencies {
    //All multiplatform libraries
    object KMM {
        object Coroutines {
            const val Core =
                "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Dependencies.KMM.CoroutinesVersion}"
            const val Android =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Dependencies.KMM.CoroutinesVersion}"
        }

        object Serialization {
            const val Json =
                "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.Dependencies.KMM.KotlinSerializationVersion}"

        }

        object Ktor {
            object Client {
                const val Core =
                    "io.ktor:ktor-client-core:${Versions.Dependencies.KMM.ktorVersion}"
                val contentNegotiation =
                    "io.ktor:ktor-client-content-negotiation:${Versions.Dependencies.KMM.ktorVersion}"
                val commonLogging =
                    "io.ktor:ktor-client-logging:${Versions.Dependencies.KMM.ktorVersion}"
                val androidOKHttp =
                    "io.ktor:ktor-client-okhttp:${Versions.Dependencies.KMM.ktorVersion}"
                val ios = "io.ktor:ktor-client-ios:${Versions.Dependencies.KMM.ktorVersion}"
                val commonSerialization =
                    "io.ktor:ktor-serialization-kotlinx-json:${Versions.Dependencies.KMM.ktorVersion}"
            }
        }

        object SQLDelight {
            const val Runtime =
                "com.squareup.sqldelight:runtime:${Versions.Dependencies.KMM.SQLDelightVersion}"
            const val coroutinesExtensions =
                "com.squareup.sqldelight:coroutines-extensions:${Versions.Dependencies.KMM.SQLDelightVersion}"
            const val AndroidDriver =
                "com.squareup.sqldelight:android-driver:${Versions.Dependencies.KMM.SQLDelightVersion}"
            const val NativeDriver =
                "com.squareup.sqldelight:native-driver:${Versions.Dependencies.KMM.SQLDelightVersion}"
        }

        object Koin {
            const val Core = "io.insert-koin:koin-core:${Versions.Dependencies.KMM.koinVersion}"
            const val Android =
                "io.insert-koin:koin-android:${Versions.Dependencies.KMM.koinVersion}"
        }

        object Realm {
            const val LibraryBase =
                "io.realm.kotlin:library-base:${Versions.Dependencies.KMM.realmVersion}"
        }

        object Settings {
            const val common =
                "com.russhwolf:multiplatform-settings:${Versions.Dependencies.KMM.multiplatform_settingsVersion}"
        }

        object Kermit {
            const val common = "co.touchlab:kermit:${Versions.Dependencies.KMM.kermit}"
        }

        object DateTimeKotlin {
            const val common =
                "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.Dependencies.KMM.DateTimeKotlinx}"
        }

        object MOKO {
            const val resources_generator =
                "dev.icerock.moko:resources-generator:${Versions.Dependencies.KMM.mokoVersion}"
            const val resources =
                "dev.icerock.moko:resources:${Versions.Dependencies.KMM.mokoVersion}"
            const val resources_compose =
                "dev.icerock.moko:resources-compose:${Versions.Dependencies.KMM.mokoVersion}"
        }
    }

    //All Android libraries
    object Android {
        const val google_android_material_Material =
            "com.google.android.material:material:${Versions.Dependencies.Android.google_android_material_version}"

        const val junit_Junit = "junit:junit:${Versions.Dependencies.Android.junit_version}"
        const val androidx_appcompat_Appcompat =
            "androidx.appcompat:appcompat:${Versions.Dependencies.Android.androidx_appcompat_version}"
        const val androidx_constraintlayout_Constraintlayout =
            "androidx.constraintlayout:constraintlayout:${Versions.Dependencies.Android.androidx_constraintlayout_version}"
        const val swipeRefresh =
            "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.Dependencies.Android.swipeRefresh}"

        object Compose {
            const val ui = "androidx.compose.ui:ui:${Versions.Dependencies.Android.Compose.compose}"
            val uiTooling =
                "androidx.compose.ui:ui-tooling:${Versions.Dependencies.Android.Compose.compose}"
            val uiToolingPreview =
                "androidx.compose.ui:ui-tooling-preview:${Versions.Dependencies.Android.Compose.compose}"
            val material =
                "androidx.compose.material:material:${Versions.Dependencies.Android.Compose.compose}"
            val activity =
                "androidx.activity:activity-compose:${Versions.Dependencies.Android.Compose.activity}"
            val liveData =
                "androidx.compose.runtime:runtime-livedata:${Versions.Dependencies.Android.Compose.compose}"
        }

        object Analvtics {
            const val mixpanel =
                "com.mixpanel.android:mixpanel-android:${Versions.Dependencies.Android.Analytics.mixpanel}"
            const val uxcam = "com.uxcam:uxcam:${Versions.Dependencies.Android.Analytics.uxcam}"
        }
    }

    //All iOS libraries
    object iOS {

    }
}