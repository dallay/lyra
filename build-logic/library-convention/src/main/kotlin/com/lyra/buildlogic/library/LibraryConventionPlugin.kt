package com.lyra.buildlogic.library

import com.lyra.buildlogic.common.ConventionPlugin
import com.lyra.buildlogic.common.extensions.catalogBundle
import com.lyra.buildlogic.common.extensions.catalogPlugin
import com.lyra.buildlogic.common.extensions.commonExtensions
import com.lyra.buildlogic.common.extensions.commonTasks
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class LibraryConventionPlugin : ConventionPlugin {
    override fun Project.configure() {
        apply(plugin = catalogPlugin("kotlin-jvm").get().pluginId)

        with(extensions) {
            commonExtensions()
        }

        tasks.commonTasks()

        dependencies {

            add("implementation", catalogBundle("kotlin-jvm"))
        }

//      testing {
//        suites {
//          // Configure the built-in test suite
//          val test by getting(JvmTestSuite::class) {
//            // Use JUnit Jupiter test framework
//            useJUnitJupiter("5.9.1")
//          }
//        }
//      }
    }
}
