package com.lyra.buildlogic.springboot

import com.lyra.buildlogic.common.ConventionPlugin
import com.lyra.buildlogic.common.extensions.catalogPlugin
import com.lyra.buildlogic.common.extensions.commonExtensions
import com.lyra.buildlogic.common.extensions.commonTasks
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class SpringBootConventionPlugin : ConventionPlugin {
  override fun Project.configure() {
    apply(plugin = catalogPlugin("kotlin-jvm").get().pluginId)
    apply(plugin = catalogPlugin("kotlin-spring").get().pluginId)
    apply(plugin = catalogPlugin("spring-boot").get().pluginId)
    apply(plugin = catalogPlugin("spring-dependency-management").get().pluginId)


    with(extensions) {
      commonExtensions()
    }

    tasks.commonTasks()

    dependencies {
      add("implementation", "org.springframework.boot:spring-boot-starter-webflux")
      add("implementation", "com.fasterxml.jackson.module:jackson-module-kotlin")
      add("implementation", "io.projectreactor.kotlin:reactor-kotlin-extensions")
      add("implementation", "org.jetbrains.kotlin:kotlin-reflect")
      add("implementation", "org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
      add("developmentOnly", "org.springframework.boot:spring-boot-devtools")
      add("developmentOnly", "org.springframework.boot:spring-boot-docker-compose")
      add("annotationProcessor", "org.springframework.boot:spring-boot-configuration-processor")
      add("testImplementation", "org.springframework.boot:spring-boot-starter-test")
      add("testImplementation", "io.projectreactor:reactor-test")
    }
  }
}
