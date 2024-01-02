plugins {
    `kotlin-dsl`
}

group = "com.lyra.app.buildlogic.common"
version = extra["app.plugins.version"].toString()


dependencies {
    implementation(libs.gradle.kotlin)
}
