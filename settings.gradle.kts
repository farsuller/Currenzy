gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))
pluginManagement {
    includeBuild("build-logic")
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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Currenzy"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core")
include(":core:network")
include(":core:model")
include(":core:data")
include(":core:design")
include(":core:database")
include(":core:common")
include(":core:testing")
include(":feature")
include(":feature:currency-converter")
