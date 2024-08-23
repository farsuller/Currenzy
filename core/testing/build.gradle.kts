plugins{
    alias(libs.plugins.currenzy.android.library)
}

android{
    namespace = "com.currenzy.testing"
}

dependencies {
    api(kotlin("test"))
    api(projects.core.data)
    api(projects.core.common)
    api(projects.core.model)
    implementation(libs.kotlinx.coroutines.test)
}