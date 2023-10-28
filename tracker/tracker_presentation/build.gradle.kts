plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "com.ondevop.tracker_presentation"
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.trackerDomain))

}