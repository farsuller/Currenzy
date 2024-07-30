plugins {
    alias(libs.plugins.currenzy.android.library)
    alias(libs.plugins.currenzy.android.hilt)
}

android {
    namespace = "com.currenzy.data"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.network)
}
