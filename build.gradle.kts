import java.nio.file.Files
import java.nio.file.StandardCopyOption

plugins {
    idea
    alias(libs.plugins.spring.boot).apply(false)
    alias(libs.plugins.spring.dependency.management).apply(false)
    kotlin("jvm").version(libs.versions.kotlin).apply(false)
    kotlin("plugin.spring").version(libs.versions.kotlin).apply(false)
    id("app.dependency-versions")
    id("app.detekt")
    id("app.kover")
    id("app.owasp.dependency.check")
}
group = rootProject.findProperty("group")?.toString() ?: "com.lyra"
version = rootProject.findProperty("version")?.toString() ?: "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

idea {
    module.isDownloadJavadoc = true
    module.isDownloadSources = true
    module.excludeDirs.add(file("**/node_modules"))
}

tasks.withType<Test>().configureEach {
    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).coerceAtLeast(1)
}

afterEvaluate {
    val filesToCopy = listOf(
        "apps/frontend/.npmrc",
        "apps/frontend/.nvmrc",
    )

    filesToCopy.forEach { filePath ->
        val sourceFile = file(filePath)
        val targetFile = file(sourceFile.name)

        if (targetFile.exists()) {
            targetFile.delete()
        }

        Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
    }
}
