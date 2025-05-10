package com.lyra.buildlogic.analysis

import com.lyra.buildlogic.common.ConventionPlugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register
import org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension
import org.owasp.dependencycheck.reporting.ReportGenerator
import java.io.File

// see https://owasp.org/www-project-dependency-check/#what-is-a-cvss-score
private const val FAIL_BUILS_ON_CVSS: Float = 11F // SET THIS TO A REASONABLE VALUE FOR YOUR PROJECT
private const val AUTO_UPDATE: Boolean = true // Enable auto-update of the NVD database
private const val PURGE_DATABASE: Boolean = true // Enable purging of the database to fix corruption issues

internal class AppOwaspPlugin : ConventionPlugin {
    override fun Project.configure() {
        apply(plugin = "org.owasp.dependencycheck")

        // Register a task to purge the dependency check database
        tasks.register<Delete>("purgeDependencyCheckDatabase") {
            description = "Purges the dependency check database to fix corruption issues"
            group = "security"

            doFirst {
                println("Purging dependency check database...")
            }

            // Delete the database files in the dependency-check-data directory
            delete(fileTree(layout.buildDirectory.dir("dependency-check-data").get().asFile) {
                include("*.h2.db")
                include("*.mv.db")  // Include odc.mv.db file
                include("*.trace.db")
                include("*.lock.db")
            })

            doLast {
                println("Dependency check database purged successfully.")
            }
        }

        // Make dependencyCheckAnalyze task depend on purgeDependencyCheckDatabase if purging is enabled
        if (PURGE_DATABASE) {
            tasks.named("dependencyCheckAnalyze").configure {
                dependsOn("purgeDependencyCheckDatabase")
            }
        }

        with(extensions) {
            configure<DependencyCheckExtension> {
                FAIL_BUILS_ON_CVSS.also { failBuildOnCVSS = it }
                formats = listOf(
                    ReportGenerator.Format.HTML.toString(),
                    ReportGenerator.Format.JUNIT.toString(),
                    ReportGenerator.Format.XML.toString(),
                    ReportGenerator.Format.SARIF.toString(),
                )
                suppressionFile = "${rootProject.rootDir}/config/owasp/owasp-supression.xml"

                setEnvironmentVariables()

                // Configure the data directory to store the NVD data and the H2 database
                data.directory =
                    layout.buildDirectory.dir("dependency-check-data").get().asFile.absolutePath

                // Enable auto-update of the NVD database
                autoUpdate = AUTO_UPDATE

                // remove plugin dependencies, for configs see
                // https://docs.gradle.org/current/userguide/java_plugin.html#sec:java_plugin_and_dependency_management
                val validConfigurations = listOf("compileClasspath", "runtimeClasspath", "default")
                scanConfigurations = configurations.names
                    .filter { validConfigurations.contains(it) }
                    .toList()
                outputDirectory = layout.buildDirectory.dir("reports/owasp").get().asFile.absolutePath
            }
        }
    }

    private fun DependencyCheckExtension.setEnvironmentVariables() {
        val apiKey = System.getenv("NVD_API_KEY")
        if (apiKey != null) {
            nvd?.apiKey = apiKey
            println(" ✅  [NVD_API_KEY] was successfully loaded from the environment.")
        } else {
            println(" ⚠️  [NVD_API_KEY] was not found in the environment. Please set it to avoid rate limiting.")
        }
        val delay = System.getenv("NVD_API_DELAY")
        if (delay != null) {
            nvd?.delay = delay.toInt()
            println(" ✅  [NVD_API_DELAY] was successfully loaded from the environment.")
        } else {
            println(" ⚠️  [NVD_API_DELAY] was not found in the environment. Defaulting to 1000ms.")
        }
    }
}
