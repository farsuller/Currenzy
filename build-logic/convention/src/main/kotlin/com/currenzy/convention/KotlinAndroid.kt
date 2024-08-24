package com.currenzy.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


/**
 * Configures Android-specific Kotlin settings for the project.
 *
 * Sets up compile SDK version, minimum SDK version, Java compatibility,
 * and enables core library desugaring. Additionally, it configures Kotlin compiler options.
 *
 * @param commonExtension The extension used to configure Android settings for the project.
 */
internal fun Project.configureKotlinAndroid(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    commonExtension.apply {
        // Set the compile SDK version for the project
        compileSdk = 34

        defaultConfig {
            // Set the minimum SDK version supported by the app
            minSdk = 26

            compileOptions {
                // Set Java compatibility versions
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
                // Enable core library desugaring to support newer Java language features on older devices
                isCoreLibraryDesugaringEnabled = true
            }

            dependencies {
                // Add the core library desugaring dependency
                add("coreLibraryDesugaring", libs.findLibrary("android.desugarJdkLibs").get())
            }

            // Configure Kotlin settings
            configureKotlin()
        }
    }
}

/**
 * Configures JVM-specific Kotlin settings for the project.
 *
 * Sets Java compatibility versions and configures Kotlin compiler options.
 */
internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        // Set Java compatibility versions for JVM projects
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // Configure Kotlin settings
    configureKotlin()
}

/**
 * Configures Kotlin compiler options for the project.
 *
 * Sets JVM target version and enables experimental Kotlin and Compose APIs.
 */
private fun Project.configureKotlin() {
    tasks.withType<KotlinCompile> {
        compilerOptions {
            // Set the JVM target version for Kotlin compilation
            jvmTarget.set(JvmTarget.JVM_11)
            // Add compiler arguments to enable experimental APIs
            freeCompilerArgs.addAll(
                listOf(
                    // Opt-in to experimental coroutines APIs, including Flow
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    // Opt-in to experimental APIs in Material3, Material, and Foundation libraries
                    "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                    "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
                    "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi"
                )
            )
        }
    }
}