import com.currenzy.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * A custom Gradle plugin for configuring Hilt dependency injection with Kotlin Symbol Processing (KSP).
 *
 * This plugin sets up the necessary configurations and dependencies for integrating Dagger Hilt into an Android project,
 * as well as configuring Kotlin Symbol Processing (KSP) for Hilt's code generation.
 *
 * In this plugin:
 * - The `com.google.devtools.ksp` and `dagger.hilt.android.plugin` plugins are applied:
 *   - `com.google.devtools.ksp`: This plugin is required for KSP, which is used for annotation processing with Hilt.
 *   - `dagger.hilt.android.plugin`: This plugin integrates Dagger Hilt for dependency injection in Android projects.
 * - Dependencies are added:
 *   - `implementation`: Adds the Hilt Android library (`hilt.android`) for dependency injection.
 *   - `ksp`: Adds the Hilt compiler (`hilt.compiler`) for code generation by KSP.
 *
 * This configuration ensures that the project is properly set up for using Hilt for dependency injection and KSP for processing
 * Hilt annotations.
 */
class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("dagger.hilt.android.plugin")
            }

            dependencies {
                "implementation"(libs.findLibrary("hilt.android").get())
                "ksp"(libs.findLibrary("hilt.compiler").get())
            }
        }
    }
}