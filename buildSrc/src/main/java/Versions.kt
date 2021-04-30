 internal object Versions {
    object Project {
        const val kotlin_version = "1.4.32"
        const val Android_Gradle_Plugin_version = "4.1.1"

    }

    object Dependencies {
        //All multiplatform libraries
        object KMM {
            const val SQLDelightVersion = "1.5.0"
            const val CoroutinesVersion = "1.4.2-native-mt"
            const val KotlinSerializationVersion = "1.0.1"
            const val ktorVersion = "1.5.3"
            const val koinVersion = "3.0.1"
            const val multiplatform_settingsVersion = "0.7.5"
        }

        //All Android libraries
        object Android {
            const val google_android_material_version="1.3.0"
            const val junit_version="4.13.1"
            const val androidx_appcompat_version="1.2.0"
            const val androidx_constraintlayout_version="2.0.4"
        }

        //All iOS libraries
        object iOS {

        }
    }

}