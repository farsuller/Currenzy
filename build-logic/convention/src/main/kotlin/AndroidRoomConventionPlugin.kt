import androidx.room.gradle.RoomExtension
import com.currenzy.convention.libs
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * A custom Gradle plugin for configuring Android projects with Room persistence library support.
 *
 * This plugin sets up the necessary configurations and dependencies for integrating Room into an Android project.
 * It includes Room's runtime and KTX extensions, and configures the Kotlin Symbol Processing (KSP) plugin to work with Room.
 *
 * In this plugin:
 * - The following plugins are applied:
 *   - `androidx.room`: This plugin provides support for Room, an Android persistence library that simplifies database access.
 *   - `com.google.devtools.ksp`: This plugin adds support for Kotlin Symbol Processing, which is used by Room for code generation.
 * - The `KspExtension` is configured to enable Room's Kotlin code generation:
 *   - `arg("room.generateKotlin", "true")`: This argument enables the generation of Kotlin code for Room's database entities and DAOs.
 * - The `RoomExtension` is configured:
 *   - `schemaDirectory("$projectDir/schemas")`: Sets the directory where Room will generate its schema files, allowing for easier schema management.
 * - The `dependencies` block adds the following dependencies:
 *   - `implementation`:
 *     - `room.runtime`: The Room runtime library for managing database interactions.
 *     - `room.ktx`: Kotlin extensions for Room, providing additional functionality and convenience methods.
 *   - `ksp`:
 *     - `room.compiler`: The Room compiler used by KSP for generating code based on Room annotations.
 *
 * This configuration ensures that the project is properly set up to use Room with Kotlin and can generate and manage database schemas.
 */
class AndroidRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("androidx.room")
            pluginManager.apply("com.google.devtools.ksp")


            extensions.configure<KspExtension>{
                arg("room.generateKotlin", "true")
            }

            extensions.configure<RoomExtension>{
                schemaDirectory("$projectDir/schemas")
            }

            dependencies {
                add("implementation", libs.findLibrary("room.runtime").get())
                add("implementation", libs.findLibrary("room.ktx").get())
                add("ksp", libs.findLibrary("room.compiler").get())
            }
        }
    }
}