import com.currenzy.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * A custom Gradle plugin for configuring Android feature modules.
 *
 * This plugin applies the necessary conventions for Android feature modules in a Gradle project.
 * It ensures that the feature module has the standard configurations and dependencies needed for Android development,
 * including integration with Hilt and Compose libraries.
 *
 * In this plugin:
 * - The `currenzy.android.library` and `currenzy.android.hilt` plugins are applied, which are expected to provide
 *   common configurations and dependency management for Android libraries and Hilt integration.
 * - Dependencies are added to include:
 *   - A project dependency on the `core:design` module.
 *   - Libraries for Hilt navigation with Compose.
 *   - Lifecycle libraries for Compose integration.
 *
 * This setup ensures that the feature module follows consistent conventions and has access to necessary libraries and tools.
 */
class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("currenzy.android.library")
                apply("currenzy.android.hilt")
            }

            dependencies {
                add("implementation", project(":core:design"))

                add("implementation", libs.findLibrary("hilt-navigation-compose").get())
                add("implementation", libs.findLibrary("androidx-lifecycle-runtimeCompose").get())
                add("implementation", libs.findLibrary("androidx-lifecycle-viewModelCompose").get())
            }
        }
    }
}