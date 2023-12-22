plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.ondevop.core"
}

dependencies {
    // Kotlin serialization
    implementation(libs.kotlinx.serialization.json)

}