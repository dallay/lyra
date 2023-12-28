import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.nio.file.Files
import kotlin.io.path.Path
import org.siouan.frontendgradleplugin.infrastructure.gradle.InstallFrontendTask

plugins {
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.21"
	id("org.siouan.frontend-jdk17")
}

group = "com.lyra"
version = "0.0.1-SNAPSHOT"

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

frontend {
	nodeVersion.set("20.10.0")
	assembleScript.set("run build")
	cleanScript.set("run clean")
	checkScript.set("run check")
	verboseModeEnabled.set(true)
}


tasks.named<InstallFrontendTask>("installFrontend") {
	val ciPlatformPresent = providers.environmentVariable("CI").isPresent
	val lockFilePath = "${projectDir}/pnpm-lock.yaml"
	val retainedMetadataFileNames: Set<String>
	if (ciPlatformPresent) {
		retainedMetadataFileNames = setOf(lockFilePath)
	} else {
		// The naive configuration below allows to skip the task if the last successful execution did not change neither
		// the package.json file, nor the pnpm-lock.yaml file, nor the node_modules directory. Any other scenario where
		// for example the lock file is regenerated will lead to another execution before the task is "up-to-date"
		// because the lock file is both an input and an output of the task.
		retainedMetadataFileNames = mutableSetOf("${projectDir}/package.json")
		if (Files.exists(Path(lockFilePath))) {
			retainedMetadataFileNames.add(lockFilePath)
		}
		outputs.file(lockFilePath).withPropertyName("lockFile")
	}
	inputs.files(retainedMetadataFileNames).withPropertyName("metadataFiles")
	outputs.dir("${projectDir}/node_modules").withPropertyName("nodeModulesDirectory")
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
