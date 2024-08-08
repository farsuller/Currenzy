import java.io.FileNotFoundException
import java.util.Properties

plugins {
    alias(libs.plugins.currenzy.android.library)
    alias(libs.plugins.currenzy.android.hilt)
    id("kotlinx-serialization")
}

val currenzyProperties: Properties by lazy {
    val properties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")

    if (localPropertiesFile.exists()) {
        properties.load(localPropertiesFile.inputStream())
    } else {
        throw FileNotFoundException("Local properties file not found.")
    }
    properties
}

android {
    namespace = "com.currenzy.network"

    defaultConfig {
        buildConfigField(type = "String",name = "API_KEY", "\"${currenzyProperties.getProperty("API_KEY")}\"")
        buildConfigField(type = "String",name = "BASE_URL", "\"${currenzyProperties.getProperty("BASE_URL")}\"")
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.retrofit.logger)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.kotlin.serialization.json)
    implementation(projects.core.model)
}
