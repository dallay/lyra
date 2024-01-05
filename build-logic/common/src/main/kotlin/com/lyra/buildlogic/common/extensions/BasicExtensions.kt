package com.lyra.buildlogic.common.extensions

import com.lyra.buildlogic.common.AppConfiguration
import java.util.*
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JvmVendorSpec
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
private val Project.libs get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

val Project.fullPackageName get() = AppConfiguration.packageName + path.replace(':', '.')

fun Project.catalogVersion(alias: String) = libs.findVersion(alias).get().toString()
fun Project.catalogLib(alias: String) = libs.findLibrary(alias).get()
fun Project.catalogBundle(alias: String) = libs.findBundle(alias).get()
fun Project.catalogPlugin(alias: String) = libs.findPlugin(alias).get()

fun KotlinDependencyHandler.catalogVersion(alias: String) = project.catalogVersion(alias)
fun KotlinDependencyHandler.catalogLib(alias: String) = project.catalogLib(alias)
fun KotlinDependencyHandler.catalogBundle(alias: String) = project.catalogBundle(alias)

fun KotlinDependencyHandler.implementation(
  dependencyNotation: Provider<*>,
  configure: ExternalModuleDependency.() -> Unit
) {
  implementation(dependencyNotation.get().toString(), configure)
}

fun DependencyHandlerScope.implementation(
  provider: Provider<*>,
  dependencyConfiguration: ExternalModuleDependency.() -> Unit = {},
) {
  "implementation"(provider, dependencyConfiguration)
}

fun ExtensionContainer.commonExtensions() {
  configure<JavaPluginExtension> {
    toolchain {
      languageVersion.set(JavaLanguageVersion.of(AppConfiguration.jvmTargetStr))
      vendor.set(JvmVendorSpec.AZUL)
    }
  }

  configure<KotlinProjectExtension> {
    jvmToolchain {
      languageVersion.set(JavaLanguageVersion.of(AppConfiguration.jvmTargetStr))
      vendor.set(JvmVendorSpec.AZUL)
    }
  }
}

fun TaskContainer.commonTasks() {
  withType<JavaCompile>().configureEach {
    sourceCompatibility = AppConfiguration.jvmTargetStr
    targetCompatibility = AppConfiguration.jvmTargetStr
  }
  withType<KotlinCompile>().configureEach {
    compilerOptions.configureKotlin()
  }
}

fun Properties.getValue(key: String, env: String) =
  getOrElse(key) { System.getenv(env) } as? String

private fun KotlinJvmCompilerOptions.configureKotlin() {
  jvmTarget.set(AppConfiguration.jvmTarget)
  apiVersion.set(AppConfiguration.kotlinVersion)
  languageVersion.set(AppConfiguration.kotlinVersion)
  freeCompilerArgs.set(
    freeCompilerArgs.get() + listOf(
      "-opt-in=kotlin.RequiresOptIn",
      "-Xcontext-receivers",
      "-Xlambdas=indy",
    ),
  )
}
