plugins {
    alias(libs.plugins.currenzy.android.library)
    alias(libs.plugins.currenzy.android.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.currenzy.network"
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.retrofit.logger)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.kotlin.serialization.json)
    implementation(projects.core.model)
}
