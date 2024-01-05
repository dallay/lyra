plugins {
    `kotlin-dsl`
}

group = "com.lyra.buildlogic.gradle"
version = extra["app.plugins.version"].toString()

dependencies {
    implementation(libs.gradle.kotlin)
    implementation(libs.gradle.updates)
    implementation(project(":common"))
}

gradlePlugin {
    plugins {
        register("dependency-versions") {
            id = "app.dependency-versions"
            implementationClass = "com.lyra.buildlogic.gradle.AppDependencyVersionsPlugin"
        }
    }
}
