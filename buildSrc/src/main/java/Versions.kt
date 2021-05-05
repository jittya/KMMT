 internal object Versions {
    object Project {
        const val kotlin_version = "1.5.0"
        const val Android_Gradle_Plugin_version = "4.1.1"

    }

    object Dependencies {
        //All multiplatform libraries
        object KMM {
            const val SQLDelightVersion = "1.5.0"
            const val CoroutinesVersion = "1.4.3-native-mt"
            const val KotlinSerializationVersion = "1.2.0"
            const val ktorVersion = "1.5.4"
            const val koinVersion = "3.0.1"
            const val multiplatform_settingsVersion = "0.7.6"
            const val kermit="0.1.8"
            const val DateTimeKotlinx = "0.2"
            const val klock = "2.1.0"
        }

        //All Android libraries
        object Android {
            const val google_android_material_version="1.3.0"
            const val junit_version="4.13.1"
            const val androidx_appcompat_version="1.2.0"
            const val androidx_constraintlayout_version="2.0.4"
            const val swipeRefresh = "1.1.0"
        }

        //All iOS libraries
        object iOS {

        }
    }

}