plugins {
    `android-library`
    `kotlin-android`
    id("com.google.gms.google-services")
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.ondevop.login_data"
}

dependencies {
    implementation(project(Modules.coreDomain))
    implementation(project(Modules.loginDomain))
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.google.auth)

    // Room
    implementation(libs.androidx.room.runtime)
    "kapt"(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
}
