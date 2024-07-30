import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins{
    `kotlin-dsl`
}

group = "com.currenzy.buildlogic"

java{
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

tasks{
    validatePlugins{
        enableStricterValidation = true
        failOnWarning = true
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin{
    plugins{

       register("androidLibrary"){
           id = "currenzy.android.library"
           implementationClass = "AndroidLibraryConventionPlugin"
       }

        register("jvmLibrary"){
            id = "currenzy.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }

        register("androidHilt"){
            id = "currenzy.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
    }
}