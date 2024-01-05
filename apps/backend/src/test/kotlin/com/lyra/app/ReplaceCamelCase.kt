package com.lyra.app

import java.lang.reflect.Method
import java.util.*
import org.junit.jupiter.api.DisplayNameGenerator

class ReplaceCamelCase : DisplayNameGenerator.Standard() {
    override fun generateDisplayNameForMethod(testClass: Class<*>?, testMethod: Method): String =
        replaceCapitals(testMethod.name)

    private fun replaceCapitals(originalName: String): String =
        originalName.replace("([A-Z])".toRegex(), " $1")
            .replace("([0-9]+)".toRegex(), " $1")
            .lowercase(Locale.getDefault())
}
