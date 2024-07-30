import com.currenzy.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("currenzy.android.library")
                apply("currenzy.android.hilt")
            }


            dependencies {
                add("implementation", project(":core:ui"))
                add("implementation", project(":core:design"))

                add("implementation",libs.findLibrary("hilt-navigation-compose").get())
                add("implementation",libs.findLibrary("androidx-lifecycle-runtimeCompose").get())
                add("implementation",libs.findLibrary("androidx-lifecycle-viewModelCompose").get())
            }
        }
    }
}