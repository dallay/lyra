package com.lyra.app.buildlogic.common

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget as KtJvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion as KtVersion

object AppConfiguration {
    const val appName = "Mindsync"
    const val packageName = "io.astrum.mindsync"

    val useJavaVersion = JavaVersion.VERSION_17
    val jvmTarget = KtJvmTarget.fromTarget(useJavaVersion.toString())
    val jvmTargetStr = jvmTarget.target
    val kotlinVersion = KtVersion.KOTLIN_1_9
}
