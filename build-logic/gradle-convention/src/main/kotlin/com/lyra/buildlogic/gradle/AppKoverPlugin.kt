package com.lyra.buildlogic.gradle

import com.lyra.buildlogic.common.ConventionPlugin
import com.lyra.buildlogic.common.extensions.isRelease
import com.lyra.buildlogic.common.extensions.kover
import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import kotlinx.kover.gradle.plugin.dsl.KoverReportExtension
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

internal class AppKoverPlugin : ConventionPlugin {
    private val classesExcludes = listOf(
        // Serializers
        "*.*$\$serializer",
    )
    private val packagesIncludes = listOf("com.lyra")
    private val packagesExcludes = listOf(
        // Common
        "*.buildlogic.*",
        "*.mock.*",
    )
    private val containerModules = listOf<String>()

    override fun Project.configure() {
        allprojects {
            if (path !in containerModules) {
                apply(plugin = "org.jetbrains.kotlinx.kover")
            }
        }

        with(extensions) {
            configure<KoverProjectExtension> { configure(project) }
            configure<KoverReportExtension> { configure() }
        }

        dependencies {
            subprojects
                .filterNot { it.path in containerModules }
                .forEach { kover(it.path) }
        }
    }

    private fun KoverProjectExtension.configure(project: Project) {
        project.tasks.withType<Test> {
            if (isRelease) disable()
        }
    }

    private fun KoverReportExtension.configure() {
        filters {
            includes { packages(packagesIncludes) }
            excludes {
                annotatedBy(
                    "androidx.compose.runtime.Composable",
                    "androidx.compose.ui.tooling.preview.Preview",
                    "com.lyra.common.domain.Generated",
                )
                classes(classesExcludes)
                packages(packagesExcludes)
                annotatedBy("*Generated*")
            }
        }
    }
}
