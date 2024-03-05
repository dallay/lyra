plugins {
    `kotlin-dsl`
}

group = "com.lyra.buildlogic.frontend"
version = extra["app.plugins.version"].toString()

dependencies {
    implementation(libs.gradle.frontend)
    implementation(project(":common"))
}

gradlePlugin {
    plugins {
        register("frontend") {
            id = "app.frontend"
            implementationClass = "com.lyra.buildlogic.frontend.FrontendPlugin"
        }
    }
}
