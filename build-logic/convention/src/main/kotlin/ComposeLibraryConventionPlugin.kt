import com.android.build.gradle.LibraryExtension
import com.currenzy.convention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * A custom Gradle plugin for configuring Android libraries to use Jetpack Compose.
 *
 * This plugin sets up the necessary configurations for integrating Jetpack Compose into Android library projects.
 * It ensures that Compose is enabled and configured properly for library modules.
 *
 * In this plugin:
 * - The `com.android.library` plugin is applied to the project:
 *   - This plugin is required for Android library modules and ensures the project is set up as an Android library.
 * - The `LibraryExtension` is configured:
 *   - The `configureAndroidCompose` function is called, which enables and configures Jetpack Compose for the library module.
 *   - This configuration includes enabling Compose features, setting up dependencies, and any other necessary setup for Compose.
 *
 * This plugin ensures that Android libraries can use Jetpack Compose by applying the correct plugin and configuration.
 */
internal class ComposeLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager){
                apply("com.android.library")
            }

            extensions.configure<LibraryExtension>{
                configureAndroidCompose(this)
            }
        }
    }
}