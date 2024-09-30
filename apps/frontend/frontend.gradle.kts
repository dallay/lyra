plugins {
    id("app.frontend")
}
val processFrontendResources = "processFrontendResources"
tasks.register<Copy>(processFrontendResources) {
    // Get the project directory
    val projectDir = project.projectDir
    println("Project directory: $projectDir")

    // Get all the directories in the apps directory
    val appDirs = projectDir.resolve("apps").listFiles { file -> file.isDirectory }
    println("App directories: ${appDirs?.joinToString()}")

    // For each app directory
    appDirs?.forEach { appDir ->
        println("⚙\uFE0E Processing app directory: ${appDir.name}")

        // If a dist directory exists in the app directory
        val distDir = appDir.resolve("dist")
        if (distDir.exists()) {
            println("Found dist directory in ${appDir.name}")

            // Define the destination directory in the build/www directory
            val destDir = "${project.layout.buildDirectory.get()}/www/${appDir.name}"
            println("Destination directory: $destDir")

            // Copy the contents of the dist directory to the destination directory
            println("Copying contents of $distDir directory to destination directory $destDir")
            copy {
                from(distDir)
                into(destDir)
            }

            mustRunAfter("assembleFrontend")
            dependsOn("installFrontend")

            doLast {
                println("⚡ Frontend resources processed")
            }
        } else {
            println("No dist directory found in ${appDir.name}")
        }
    }
}

tasks.named("assemble") {
    dependsOn(processFrontendResources)
    dependsOn("copyEnvFile")
}

tasks.named("build") {
    dependsOn(processFrontendResources)
    dependsOn("copyEnvFile")
}
