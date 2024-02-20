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
        mavenLocal()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

rootProject.name = "Jitpack Artifact PoC"
include(":app")
include(":mylibrary")
