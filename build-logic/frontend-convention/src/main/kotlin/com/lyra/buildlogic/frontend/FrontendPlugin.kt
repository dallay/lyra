package com.lyra.buildlogic.frontend

import com.lyra.buildlogic.common.ConventionPlugin
import java.nio.file.Files
import kotlin.io.path.Path
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.named
import org.siouan.frontendgradleplugin.infrastructure.gradle.FrontendExtension
import org.siouan.frontendgradleplugin.infrastructure.gradle.InstallFrontendTask

private const val DEFAULT_NODE_VERSION = "20.11.1"

internal class FrontendPlugin : ConventionPlugin {

    override fun Project.configure() {
        apply(plugin = "org.siouan.frontend-jdk17")
        with(extensions) {
            configure<FrontendExtension> { configure(project) }
        }
        tasks.named<InstallFrontendTask>("installFrontend") {
            val ciPlatformPresent = providers.environmentVariable("CI").isPresent
            val lockFilePath = "$projectDir/pnpm-lock.yaml"
            val retainedMetadataFileNames: Set<String>
            if (ciPlatformPresent) {
                retainedMetadataFileNames = setOf(lockFilePath)
            } else {
                 /*The naive configuration below allows to skip the task
                   if the last successful execution did not change neither
                   the package.json file, nor the pnpm-lock.yaml file,
                   nor the node_modules directory. Any other scenario where
                   for example the lock file is regenerated will lead
                   to another execution before the task is "up-to-date"
                   because the lock file is both an input and an output of the task.
                  */
                retainedMetadataFileNames = mutableSetOf("$projectDir/package.json")
                if (Files.exists(Path(lockFilePath))) {
                    retainedMetadataFileNames.add(lockFilePath)
                }
                outputs.file(lockFilePath).withPropertyName("lockFile")
            }
            inputs.files(retainedMetadataFileNames).withPropertyName("metadataFiles")
            outputs.dir("$projectDir/node_modules").withPropertyName("nodeModulesDirectory")
        }
    }

    private fun FrontendExtension.configure(project: Project) {
        fun loadNodeVersion() = project.rootProject.file(".node-version").let { file ->
            if (file.exists()) {
                file.readText().trim()
            } else {
                DEFAULT_NODE_VERSION
            }
        }.also { println("\uD83D\uDFE2 Using Node.js version $it") }

        nodeVersion.set(loadNodeVersion())
        assembleScript.set("run build")
        cleanScript.set("run clean")
        checkScript.set("run check")
        packageJsonDirectory.set(project.file("apps/frontend"))
        nodeInstallDirectory.set(project.rootProject.file(".node"))
        verboseModeEnabled.set(true)
    }
}
