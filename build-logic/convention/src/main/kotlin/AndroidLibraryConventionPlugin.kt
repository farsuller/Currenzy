import com.android.build.gradle.LibraryExtension
import com.currenzy.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

/**
 * A custom Gradle plugin for configuring Android library projects with Kotlin support.
 *
 * This plugin applies common configurations and dependencies for Android library modules.
 * It sets up the project to use Android library plugin, Kotlin Android plugin, and configures
 * Kotlin for Android. Additionally, it adds dependencies for unit testing.
 *
 * In this plugin:
 * - The following plugins are applied:
 *   - `com.android.library`: This plugin is required for Android library projects, providing support for Android-specific tasks.
 *   - `org.jetbrains.kotlin.android`: This plugin adds Kotlin support for Android projects, enabling Kotlin language features and Kotlin-specific tasks.
 * - The `LibraryExtension` is configured:
 *   - `configureKotlinAndroid(this)`: This function configures Kotlin-specific settings for Android, such as Kotlin version and JVM target.
 * - The `testImplementation` dependency configuration is updated to include Kotlin's `test` library:
 *   - `kotlin("test")`: This dependency provides testing utilities for Kotlin, allowing for writing and running unit tests.
 *
 * This configuration ensures that Android library modules are properly set up for Kotlin development and include necessary testing libraries.
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
            }

            dependencies {
                add("testImplementation", kotlin("test"))
            }
        }
    }
}