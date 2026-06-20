pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "NewsDigest"
include(
    ":app",
    ":appDependencies",
    ":domain",
    ":repoImpl",
    ":appSupport",
    ":common",
    ":utils",
    ":design-system",
    ":design-system-catalog",
)
include(":data:local", ":data:remote")
project(":data:local").projectDir = file("data/local")
project(":data:remote").projectDir = file("data/remote")
