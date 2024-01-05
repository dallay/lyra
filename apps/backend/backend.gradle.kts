import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("app.spring.boot.convention")
    kotlin("jvm").version(libs.versions.kotlin)
    kotlin("plugin.spring").version(libs.versions.kotlin)
    alias(libs.plugins.gradle.git.properties)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // L O C A L   D E P E N D E N C I E S
    implementation(project(":shared:common"))
}

tasks.register<Copy>("processFrontendResources") {
    val frontendBuildDir = "${project(":apps:frontend").layout.buildDirectory.get()}/www"
    val frontendResourcesDir = "${layout.buildDirectory.get()}/resources/main/static"

    group = "Frontend"
    description = "Process frontend resources"
    dependsOn(":apps:frontend:assembleFrontend")

    from(frontendBuildDir)
    into(frontendResourcesDir)
}

tasks.named<Task>("processResources") {
    dependsOn("processFrontendResources")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
