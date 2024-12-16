import org.gradle.kotlin.dsl.support.listFilesOrdered

rootProject.name = "lyra"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        maven("https://androidx.dev/storage/compose-compiler/repository")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
    }
}

pluginManagement {
    includeBuild("build-logic")
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

plugins {
    id("com.gradle.develocity") version "3.19"
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

// Keep in sync with build-logic/settings.gradle.kts
buildCache {
    local {
        directory = rootDir.resolve(".gradle/build-cache")
    }
}

val defaultBuildGradleKts = "build.gradle.kts"

fun isGradleKtsProjectDirectory(directory: File) =
    directory.isDirectory &&
        (
            directory.resolve(defaultBuildGradleKts).exists() ||
                directory.resolve("${directory.name}.gradle.kts").exists()
            ) &&
        directory.name !in excludedProjects

fun includeGradleProjectsRecursively(directoryPath: String) {
    val baseDirectory = rootDir.resolve(directoryPath)
    baseDirectory.walkTopDown()
        .maxDepth(1)
        .filter { it.isDirectory }
        .forEach { subDir ->
            includeProjectsInDirectory(subDir.path)
        }
}

fun includeProject(dir: File) {
    println("\uD83D\uDCE6 Loading submodule: ${dir.name}")
    val projectName = calculateProjectName(dir)
    include(projectName)
    val prj = project(":$projectName")
    prj.projectDir = dir
    prj.buildFileName = if (dir.resolve("${dir.name}.gradle.kts").exists()) {
        "${dir.name}.gradle.kts"
    } else {
        defaultBuildGradleKts
    }
    require(prj.projectDir.isDirectory) { "Project '${prj.path} must have a ${prj.projectDir} directory" }
    require(prj.buildFile.isFile) {
        "Project '${prj.path} must have a ${prj.buildFile} build script file or ${prj.buildFile.name}.gradle.kts"
    }
}

fun calculateProjectName(dir: File): String {
    val projectName = dir.relativeTo(rootDir).path.replace("/", ":")
    println("\uD83D\uDFE2 Project name: $projectName")
    return if (projectName.startsWith(":")) projectName.substring(1) else projectName
}

fun includeProjectsInDirectory(directoryPath: String) {
    val baseDirectory = rootDir.resolve(directoryPath)
    println("\uD83D\uDFE3 Loading projects from \uD83D\uDCC2  $baseDirectory")
    baseDirectory.listFilesOrdered()
        .filter { isGradleKtsProjectDirectory(it) }
        .forEach { projectDirectory ->
            includeProject(projectDirectory)
        }
}

val excludedProjects = listOf<String>()
val projects = listOf("apps", "shared")
projects.forEach { includeGradleProjectsRecursively(it) }

// includeProject(file("documentation"))

develocity {
    buildScan {
        termsOfUseUrl = "https://gradle.com/terms-of-service"
        termsOfUseAgree = "yes"
        tag("CI")
    }
}
