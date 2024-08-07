plugins {
    alias(libs.plugins.currenzy.android.feature)
    alias(libs.plugins.currenzy.compose.library)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.currenzy.currenzy.converter"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.model)
}

