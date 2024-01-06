plugins {
    `kotlin-dsl`
}

group = "com.lyra.buildlogic.common"
version = extra["app.plugins.version"].toString()

dependencies {
    implementation(libs.gradle.kotlin)
}
