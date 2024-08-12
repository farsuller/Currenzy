import java.io.FileNotFoundException
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.currenzy.android.hilt)
    alias(libs.plugins.currenzy.android.room)
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
    namespace = ProjectVersionConfig.NAMESPACE
    compileSdk = ProjectVersionConfig.COMPILE_SDK

    defaultConfig {
        applicationId = ProjectVersionConfig.APPLICATION_ID
        minSdk = ProjectVersionConfig.MIN_SDK
        targetSdk = ProjectVersionConfig.TARGET_SDK
        versionCode = ProjectVersionConfig.VERSION_CODE
        versionName = "${ProjectVersionConfig.MAJOR_VERSION}.${ProjectVersionConfig.MINOR_VERSION}.${ProjectVersionConfig.PATCH_VERSION}"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    applicationVariants.all {
        base.archivesName.set("${ProjectVersionConfig.APP_FILENAME}-${buildType.name}-$versionCode-$versionName")
    }

    signingConfigs {
        register("release") {
            storeFile = file("keystore/currenzy.jks")
            storePassword = currenzyProperties["releaseStorePassword"].toString()
            keyAlias = currenzyProperties["releaseKeyAlias"].toString()
            keyPassword = currenzyProperties["releaseKeyPassword"].toString()
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
            isMinifyEnabled = false
        }

        release {
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.material3)

    coreLibraryDesugaring(libs.android.desugarJdkLibs)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(projects.core.network)
    implementation(projects.core.design)
    implementation(projects.core.model)
    implementation(projects.core.database)
    implementation(projects.feature.currencyConverter)
}