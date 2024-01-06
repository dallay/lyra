plugins {
    `kotlin-dsl`
}

group = "com.lyra.buildlogic.springboot"
version = extra["app.plugins.version"].toString()

dependencies {
    implementation(libs.gradle.kotlin)
    implementation(project(":common"))
}

gradlePlugin {
    plugins {
        register("spring-boot-convention") {
            id = "app.spring.boot.convention"
            implementationClass = "com.lyra.buildlogic.springboot.SpringBootConventionPlugin"
        }
    }
}
