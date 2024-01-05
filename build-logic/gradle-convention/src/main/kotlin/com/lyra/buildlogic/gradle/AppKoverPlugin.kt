package com.lyra.buildlogic.gradle

import com.lyra.buildlogic.common.ConventionPlugin
import com.lyra.buildlogic.common.extensions.isRelease
import com.lyra.buildlogic.common.extensions.kover
import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import kotlinx.kover.gradle.plugin.dsl.KoverReportExtension
import kotlinx.kover.gradle.plugin.dsl.KoverVerifyReportConfig
import kotlinx.kover.gradle.plugin.dsl.MetricType
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
      configure<KoverReportExtension> { configure(project) }
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

  private fun KoverReportExtension.configure(project: Project) {
    filters {
      includes { packages(packagesIncludes) }
      excludes {
        annotatedBy(
          "androidx.compose.runtime.Composable",
          "androidx.compose.ui.tooling.preview.Preview",
        )
        classes(classesExcludes)
        packages(packagesExcludes)
        // excludes all classes and functions, annotated by specified annotations (with BINARY or RUNTIME AnnotationRetention), wildcards '*' and '?' are available
        annotatedBy("*Generated*")
      }
    }

//    defaults{
//      xml{
//        setReportFile(project.file("build/reports/kover/kover.xml"))
//      }
//    }

  }

  private fun KoverVerifyReportConfig.configure() {
    onCheck = true

    rule("Minimal instruction coverage rate in percent") {
      bound {
        metric = MetricType.INSTRUCTION
        minValue = MIN_COVERED_PERCENTAGE
      }
    }
    rule("Minimal line coverage rate in percent") {
      bound {
        metric = MetricType.LINE
        minValue = MIN_COVERED_PERCENTAGE
      }
    }
  }

  private companion object {
    const val ANDROID_VARIANT = "debug"
    const val MIN_COVERED_PERCENTAGE = 80
  }
}
