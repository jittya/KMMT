import org.gradle.kotlin.dsl.`kotlin-dsl`

repositories{
    jcenter()
}
plugins {
    `kotlin-dsl`
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}