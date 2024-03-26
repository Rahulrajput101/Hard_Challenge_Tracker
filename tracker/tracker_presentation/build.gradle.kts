plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "com.ondevop.tracker_presentation"
}

dependencies {
    implementation(project(Modules.coreDomain))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.trackerDomain))
    implementation(libs.google.play.billing)
    implementation(libs.firebase.crashlytics.buildtools)

}