object Dependencies {
    //All multiplatform libraries
    object Common {
        object Coroutines {
            const val Core =
                "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Dependencies.Common.CoroutinesVersion}"
            const val Android =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Dependencies.Common.CoroutinesVersion}"
        }

        object Serialization {
            const val Json =
                "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.Dependencies.Common.KotlinSerializationVersion}"

        }

        object Ktor {
            object Client {
                const val Core =
                    "io.ktor:ktor-client-core:${Versions.Dependencies.Common.ktorVersion}"
                val commonJson = "io.ktor:ktor-client-json:${Versions.Dependencies.Common.ktorVersion}"
                val commonLogging = "io.ktor:ktor-client-logging:${Versions.Dependencies.Common.ktorVersion}"
                val androidOKHttp = "io.ktor:ktor-client-okhttp:${Versions.Dependencies.Common.ktorVersion}"
                val ios = "io.ktor:ktor-client-ios:${Versions.Dependencies.Common.ktorVersion}"
                val commonSerialization =
                    "io.ktor:ktor-client-serialization:${Versions.Dependencies.Common.ktorVersion}"
            }
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
        }

        //All iOS libraries
        object iOS {

        }
    }