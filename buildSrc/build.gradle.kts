import org.gradle.kotlin.dsl.`kotlin-dsl`

//repositories {
//    mavenCentral()
//}
//
//
//plugins {
//    `kotlin-dsl`
//}
//
plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.22")
    implementation("com.android.tools.build:gradle:8.1.1")
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "18"
}