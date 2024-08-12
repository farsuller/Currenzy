plugins {
    alias(libs.plugins.currenzy.android.library)
    alias(libs.plugins.currenzy.android.room)
    alias(libs.plugins.currenzy.android.hilt)
}

android {
    namespace = "com.currenzy.data"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.network)
    implementation(projects.core.common)
    implementation(projects.core.database)

    implementation(libs.androidx.work)
    implementation(libs.hilt.ext.work)
}
