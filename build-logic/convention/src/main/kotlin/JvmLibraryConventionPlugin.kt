import com.currenzy.convention.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * A custom Gradle plugin for configuring Kotlin JVM libraries.
 *
 * This plugin sets up the necessary configurations for Kotlin JVM projects.
 * It ensures that Kotlin is properly configured for JVM library modules.
 *
 * In this plugin:
 * - The `org.jetbrains.kotlin.jvm` plugin is applied to the project:
 *   - This plugin is essential for Kotlin JVM projects and provides support for compiling Kotlin code to JVM bytecode.
 * - The `configureKotlinJvm` function is called:
 *   - This function configures Kotlin-specific settings for JVM libraries, such as setting the JVM target version and adding compiler arguments.
 *
 * This plugin ensures that Kotlin JVM libraries are correctly configured to compile and run on the JVM with the appropriate settings.
 */
internal class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("org.jetbrains.kotlin.jvm")
            }
            configureKotlinJvm()
        }
    }
}