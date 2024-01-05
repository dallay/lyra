plugins {
    `kotlin-dsl`
}

group = "com.lyra.buildlogic.library"
version = extra["app.plugins.version"].toString()

dependencies {
    implementation(libs.gradle.kotlin)
    implementation(project(":common"))
}

gradlePlugin {
    plugins {
        register("library-convention") {
            id = "app.library.convention"
            implementationClass = "com.lyra.buildlogic.library.LibraryConventionPlugin"
        }
    }
}
