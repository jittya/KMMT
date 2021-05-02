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
                val commonJson = "io.ktor:ktor-client-json:${Versions.Dependencies.KMM.ktorVersion}"
                val commonLogging = "io.ktor:ktor-client-logging:${Versions.Dependencies.KMM.ktorVersion}"
                val androidOKHttp = "io.ktor:ktor-client-okhttp:${Versions.Dependencies.KMM.ktorVersion}"
                val ios = "io.ktor:ktor-client-ios:${Versions.Dependencies.KMM.ktorVersion}"
                val commonSerialization =
                    "io.ktor:ktor-client-serialization:${Versions.Dependencies.KMM.ktorVersion}"
            }
        }

        object SQLDelight {
            const val Runtime = "com.squareup.sqldelight:runtime:${Versions.Dependencies.KMM.SQLDelightVersion}"
            const val coroutinesExtensions =
                "com.squareup.sqldelight:coroutines-extensions:${Versions.Dependencies.KMM.SQLDelightVersion}"
            const val AndroidDriver =
                "com.squareup.sqldelight:android-driver:${Versions.Dependencies.KMM.SQLDelightVersion}"
            const val NativeDriver =
                "com.squareup.sqldelight:native-driver:${Versions.Dependencies.KMM.SQLDelightVersion}"
        }

        object Koin {
            const val Core = "io.insert-koin:koin-core:${Versions.Dependencies.KMM.koinVersion}"
            const val Android = "io.insert-koin:koin-android:${Versions.Dependencies.KMM.koinVersion}"
        }

        object Settings {
            const val common =
                "com.russhwolf:multiplatform-settings:${Versions.Dependencies.KMM.multiplatform_settingsVersion}"
        }

        object Kermit {
            const val common = "co.touchlab:kermit:${Versions.Dependencies.KMM.kermit}"
        }
        object DateTimeKotlin
        {
            const val common= "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.Dependencies.KMM.DateTimeKotlinx}"
        }
        object  Klock{
            const val common= "com.soywiz.korlibs.klock:klock:${Versions.Dependencies.KMM.klock}"
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
    }

    //All iOS libraries
    object iOS {

    }
}