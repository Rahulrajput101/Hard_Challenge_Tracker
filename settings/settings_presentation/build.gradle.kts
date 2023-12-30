plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "com.ondevop.settings_presentation"
}

dependencies {
    implementation(project(Modules.coreDomain))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.settingsDomain))
}