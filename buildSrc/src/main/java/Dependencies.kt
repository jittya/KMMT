object Dependencies {
    //All multiplatform libraries
    object Common {

    }

    //All Android libraries
    object Android {
        const val google_android_material_Material =
            "com.google.android.material:material:${Versions.Dependencies.Android.google_android_material_version}"

        const val junit_Junit = "junit:junit:${Versions.Dependencies.Android.junit_version}"
        const val androidx_appcompat_Appcompat =
            "androidx.appcompat:appcompat:${Versions.Dependencies.Android.androidx_appcompat_version}"
    const val androidx_constraintlayout_Constraintlayout="androidx.constraintlayout:constraintlayout:${Versions.Dependencies.Android.androidx_constraintlayout_version}"
    }

    //All iOS libraries
    object iOS {

    }
}