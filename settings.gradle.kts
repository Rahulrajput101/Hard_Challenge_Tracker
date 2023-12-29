pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "75 Hard"
include(":app")
include(":core")
include(":onboarding")
include(":onboarding:onboarding_presentation")
include(":onboarding:onboarding_domain")
include(":login")
include(":login:login_domain")
include(":login:login_presentation")
include(":login:login_data")
include(":tracker")
include(":tracker:tracker_domain")
include(":tracker:tracker_presentation")
include(":core-ui")
include(":settings")
include(":settings:settings_presentation")
include(":settings:settings_data")
include(":settings:settings_domain")
