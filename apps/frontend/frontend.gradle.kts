plugins {
    alias(libs.plugins.frontend.gradle.plugin)
}

frontend {
    nodeVersion.set("20.10.0")
    assembleScript.set("run build")
    cleanScript.set("run clean")
    checkScript.set("run check")
    verboseModeEnabled.set(true)
}
