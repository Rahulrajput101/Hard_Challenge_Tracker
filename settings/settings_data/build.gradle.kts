plugins {
    `android-library`
    `kotlin-android`
    id("com.google.gms.google-services")
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.ondevop.settings_data"
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.settingsDomain))
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.google.auth)

}
