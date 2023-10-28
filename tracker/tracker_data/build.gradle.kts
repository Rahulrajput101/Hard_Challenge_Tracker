plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.ondevop.tracker_data"
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.trackerDomain))

    // Room
    implementation(libs.androidx.room.runtime)
     "kapt"(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
}
