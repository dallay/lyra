package com.lyra.buildlogic.springboot

import com.lyra.buildlogic.common.ConventionPlugin
import com.lyra.buildlogic.common.extensions.catalogBundle
import com.lyra.buildlogic.common.extensions.catalogPlugin
import com.lyra.buildlogic.common.extensions.commonExtensions
import com.lyra.buildlogic.common.extensions.commonTasks
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

private const val TEST_IMPLEMENTATION = "testImplementation"

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
            add("implementation", catalogBundle("spring-boot"))

            add("implementation", catalogBundle("kotlin-jvm"))

            add("developmentOnly", catalogBundle("spring-boot-dev"))

            add("annotationProcessor", "org.springframework.boot:spring-boot-configuration-processor")

            add(TEST_IMPLEMENTATION, catalogBundle("spring-boot-test"))

            add(TEST_IMPLEMENTATION, catalogBundle("tetscontainers"))

            add(TEST_IMPLEMENTATION, catalogBundle("jjwt"))

            add(TEST_IMPLEMENTATION, catalogBundle("kotest"))
        }
    }
}
