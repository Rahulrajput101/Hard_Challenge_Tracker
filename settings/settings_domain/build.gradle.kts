plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.ondevop.settings_domain"
}

dependencies {
    implementation(project(Modules.coreDomain))
}