package com.lyra.buildlogic.analysis

import com.lyra.buildlogic.common.ConventionPlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension
import org.owasp.dependencycheck.reporting.ReportGenerator

// see https://owasp.org/www-project-dependency-check/#what-is-a-cvss-score
private const val FAIL_BUILS_ON_CVSS: Float = 11F // SET THIS TO A REASONABLE VALUE FOR YOUR PROJECT

internal class AppOwaspPlugin : ConventionPlugin {
    override fun Project.configure() {
        apply(plugin = "org.owasp.dependencycheck")

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
                // ENV VAR: OWASP_API_KEY
                val apiKey = System.getenv("NVD_API_KEY")
                if (apiKey != null) {
                    nvd?.apiKey = apiKey
                    println("✅ NVD_API_KEY was successfully loaded from the environment.")
                } else {
                    println("⚠️ NVD_API_KEY was not found in the environment. Please set it to avoid rate limiting.")
                }

                // Configure the data directory to store the NVD data and the H2 database
                data.directory =
                    layout.buildDirectory.dir("dependency-check-data").get().asFile.absolutePath

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
}
