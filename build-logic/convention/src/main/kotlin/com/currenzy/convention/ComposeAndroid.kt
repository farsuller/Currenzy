package com.currenzy.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
/**
 * Configures Android Compose settings and dependencies for a Gradle project.
 *
 * This function enables Compose features and sets up necessary dependencies
 * for using Jetpack Compose in the project.
 *
 * @param commonExtension The extension used to configure Android settings for the project.
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        // Enable Jetpack Compose build features
        buildFeatures {
            compose = true
        }

        // Configure dependencies for Jetpack Compose
        dependencies {
            // Get the BOM (Bill of Materials) for Compose dependencies
            val bom = libs.findLibrary("androidx-compose-bom").get()

            // Add Compose dependencies
            add("implementation", platform(bom)) // Adds BOM for managing Compose library versions
            add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get()) // For previewing Compose UI
            add("implementation", libs.findLibrary("androidx-material3").get()) // Material Design 3 components for Compose
            add("implementation", libs.findLibrary("androidx-lifecycle-runtimeCompose").get()) // Lifecycle-aware components for Compose
            add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get()) // Tooling support for Compose in debug builds
            add("androidTestImplementation", platform(bom)) // Adds BOM for testing Compose UI
        }
    }
}