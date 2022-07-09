object Plugins {
    const val SQLDelightGradlePlugin =
        "com.squareup.sqldelight:gradle-plugin:${Versions.Dependencies.KMM.SQLDelightVersion}"
    const val kotlinGradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Project.kotlin_version}"
    const val kotlinSerializationGradlePlugin =
        "org.jetbrains.kotlin:kotlin-serialization:${Versions.Project.kotlin_version}"
    const val AndroidBuildGradlePlugin =
        "com.android.tools.build:gradle:${Versions.Project.Android_Gradle_Plugin_version}"
    const val RealmGradlePlugin =
        "io.realm.kotlin:gradle-plugin:${Versions.Project.Realm_Gradle_Plugin_version}"
    const val MokoResourceGenerator = Dependencies.KMM.MOKO.resources_generator
}