package com.lyra.buildlogic.springboot

import com.lyra.buildlogic.common.ConventionPlugin
import com.lyra.buildlogic.common.extensions.catalogBundle
import com.lyra.buildlogic.common.extensions.catalogPlugin
import com.lyra.buildlogic.common.extensions.commonExtensions
import com.lyra.buildlogic.common.extensions.commonTasks
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class SpringBootLibraryConventionPlugin : ConventionPlugin {
    override fun Project.configure() {
        apply(plugin = catalogPlugin("kotlin-jvm").get().pluginId)
        apply(plugin = catalogPlugin("kotlin-spring").get().pluginId)

        with(extensions) {
            commonExtensions()
        }

        tasks.commonTasks()

        dependencies {
            add("implementation", catalogBundle("spring-boot"))

            add("implementation", catalogBundle("kotlin-jvm"))

            add("testImplementation", catalogBundle("spring-boot-test"))
        }
    }
}
