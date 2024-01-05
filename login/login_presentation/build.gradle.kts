plugins {
    `android-library`
    `kotlin-android`
    id("com.google.gms.google-services")
}

apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "com.ondevop.login_presentation"
}

dependencies {
    implementation(project(Modules.coreDomain))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.loginDomain))
    implementation(libs.coil.compose)
    implementation(libs.google.auth)

}