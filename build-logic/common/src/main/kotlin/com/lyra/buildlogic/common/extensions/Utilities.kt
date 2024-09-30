package com.lyra.buildlogic.common.extensions

import java.nio.file.Files
import org.gradle.api.Project

/**
 * Registers a Gradle task named "copyEnvFile" that creates symbolic links for the .env file
 * in the specified locations.
 *
 * @param locations A list of relative paths where the .env file should be linked.
 */
fun Project.registerCopyEnvFileTask(locations: List<String>) {
    tasks.register("copyEnvFile") {
        doLast {
            val rootEnvFile = project.rootDir.resolve(".env").toPath()
            if (Files.exists(rootEnvFile)) {
                locations.forEach { location ->
                    val targetPath = project.rootDir.resolve(location).resolve(".env").toPath()
                    if (Files.exists(targetPath)) {
                        Files.delete(targetPath)
                    }
                    Files.createSymbolicLink(targetPath, rootEnvFile)
                }
            } else {
                println("↠ ⚠\uFE0F ⚠\uFE0E Warning: .env file not found at $rootEnvFile")
            }
        }
    }
}
