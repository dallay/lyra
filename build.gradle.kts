import java.nio.file.Files
import kotlin.io.path.Path
import org.siouan.frontendgradleplugin.infrastructure.gradle.InstallFrontendTask

plugins {
    idea
    alias(libs.plugins.spring.boot).apply(false)
    alias(libs.plugins.spring.dependency.management).apply(false)
    kotlin("jvm").version(libs.versions.kotlin).apply(false)
    kotlin("plugin.spring").version(libs.versions.kotlin).apply(false)
    id("app.dependency-versions")
    id("app.detekt")
    id("app.owasp.dependency.check")
    alias(libs.plugins.frontend.gradle.plugin)
}
group = rootProject.findProperty("group")?.toString() ?: "com.lyra"
version = rootProject.findProperty("version")?.toString() ?: "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

idea {
    module.isDownloadJavadoc = true
    module.isDownloadSources = true
    // exclude node_modules from indexing
    module.excludeDirs.add(file("**/node_modules"))
}

frontend {
    nodeVersion.set("20.10.0")
    assembleScript.set("run build")
    cleanScript.set("run clean")
    checkScript.set("run check")
    verboseModeEnabled.set(true)
}

dependencies {
}

tasks.named<InstallFrontendTask>("installFrontend") {
    val ciPlatformPresent = providers.environmentVariable("CI").isPresent
    val lockFilePath = "$projectDir/pnpm-lock.yaml"
    val retainedMetadataFileNames: Set<String>
    if (ciPlatformPresent) {
        retainedMetadataFileNames = setOf(lockFilePath)
    } else {
        // The naive configuration below allows to skip the task if the last successful execution did not change neither
        // the package.json file, nor the pnpm-lock.yaml file, nor the node_modules directory. Any other scenario where
        // for example the lock file is regenerated will lead to another execution before the task is "up-to-date"
        // because the lock file is both an input and an output of the task.
        retainedMetadataFileNames = mutableSetOf("$projectDir/package.json")
        if (Files.exists(Path(lockFilePath))) {
            retainedMetadataFileNames.add(lockFilePath)
        }
        outputs.file(lockFilePath).withPropertyName("lockFile")
    }
    inputs.files(retainedMetadataFileNames).withPropertyName("metadataFiles")
    outputs.dir("$projectDir/node_modules").withPropertyName("nodeModulesDirectory")
}
