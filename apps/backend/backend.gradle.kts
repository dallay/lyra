import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

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
    implementation(project(":shared:spring-boot-common"))

    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation(libs.spring.boot.starter.data.r2dbc)
    implementation(libs.bundles.kotlin.jvm)
    implementation(libs.commons.text)
    implementation("org.liquibase:liquibase-core")
    implementation("org.springframework:spring-r2dbc")
    implementation("org.springframework.data:spring-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine")
    implementation("org.postgresql:r2dbc-postgresql")
    runtimeOnly("org.postgresql:postgresql:42.7.3")

    // SECURITY DEPENDENCIES
    implementation("org.springframework.security:spring-security-oauth2-client")
    implementation("org.springframework.security:spring-security-oauth2-jose")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    implementation(libs.spring.dotenv)
    implementation(libs.sendgrid)
    implementation(libs.bundles.keycloak)
    implementation(libs.jsoup)

    testImplementation(libs.faker)
    testImplementation(libs.mockk)

    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:r2dbc")
    testImplementation("com.tngtech.archunit:archunit:1.3.0")
}
val processFrontendResources = "processFrontendResources"
tasks.register<Copy>(processFrontendResources) {
    val www = "${project(":apps:frontend").layout.buildDirectory.get()}/www"
    val frontendBuildDir = "$www/lyra-app"
    val frontendResourcesDir = "${layout.buildDirectory.get()}/resources/main/static"

    group = "Frontend"
    description = "Process frontend resources"
    dependsOn(":apps:frontend:$processFrontendResources")
    mustRunAfter(":apps:frontend:assembleFrontend")

    copy {
        from(frontendBuildDir)
        into("$frontendResourcesDir/app")
    }

    copy {
        from("$www/lyra-landing-page")
        into(frontendResourcesDir)
    }

    doLast {
        println("⚡ Frontend resources processed and copied to $frontendResourcesDir")
    }
}

tasks.named<Task>("processResources") {
    dependsOn(processFrontendResources)
}

tasks.named("build") {
    dependsOn(processFrontendResources)
    dependsOn("copyEnvFile")
}

tasks.named("assemble") {
    dependsOn(processFrontendResources)
    dependsOn("copyEnvFile")
}

tasks.named<BootBuildImage>("bootBuildImage") {
    buildCache {
        volume {
            name.set("cache-${rootProject.name}-deps")
        }
    }

    launchCache {
        volume {
            name.set("launch-cache-${rootProject.name}")
        }
    }

    imageName = "lyra"
    createdDate = "now"

    tags = listOf(
        "lyra:${project.version}",
        "ghcr.io/dallay/lyra:latest",
        "ghcr.io/dallay/lyra:${project.version}",
        "docker.io/n4t5u/lyra:latest",
        "docker.io/n4t5u/lyra:${project.version}",
    )

    dependsOn("build")
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
