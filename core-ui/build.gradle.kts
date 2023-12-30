plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "com.ondevop.core_ui"
}

dependencies {
    implementation(project(Modules.coreDomain))
    implementation(libs.coil.compose)
}