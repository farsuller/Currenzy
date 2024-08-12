plugins{
    alias(libs.plugins.currenzy.android.library)
    alias(libs.plugins.currenzy.android.room)
    alias(libs.plugins.currenzy.android.hilt)
}

android{
    namespace = "com.currenzy.database"
}

dependencies {
    implementation(projects.core.model)
}