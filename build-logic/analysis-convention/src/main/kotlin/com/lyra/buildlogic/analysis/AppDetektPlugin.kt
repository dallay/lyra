package com.lyra.buildlogic.analysis

import com.lyra.buildlogic.common.AppConfiguration
import com.lyra.buildlogic.common.ConventionPlugin
import com.lyra.buildlogic.common.extensions.catalogLib
import com.lyra.buildlogic.common.extensions.detekt
import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register

internal class AppDetektPlugin : ConventionPlugin {
    override fun Project.configure() {
        apply(plugin = "io.gitlab.arturbosch.detekt")

        tasks.register<Detekt>("detektAll") {
            description = "Run detekt in all modules"

            parallel = true
            ignoreFailures = false
            autoCorrect = true
            buildUponDefaultConfig = true
            jvmTarget = AppConfiguration.jvmTargetStr
            setSource(files(projectDir))
            config.setFrom(files("$rootDir/config/detekt.yml"))
            include("**/*.kt", "**/*.kts")
            exclude("**/resources/**", "**/build/**")

            reports {
                html.required.set(true)
                sarif.required.set(true)
                txt.required.set(false)
                xml.required.set(true)
            }
        }

        dependencies {
            detekt(catalogLib("detekt-compose"))
            detekt(catalogLib("detekt-compose2"))
            detekt(catalogLib("detekt-formatting"))
        }
    }
}
