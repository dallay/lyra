import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    kotlin("jvm").version(libs.versions.kotlin)
    kotlin("plugin.spring").version(libs.versions.kotlin)
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
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
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
