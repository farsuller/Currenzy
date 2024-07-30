plugins{
    alias(libs.plugins.currenzy.jvm.library)
    id("kotlinx-serialization")
}

dependencies {
    implementation(libs.kotlin.serialization.json)
}

