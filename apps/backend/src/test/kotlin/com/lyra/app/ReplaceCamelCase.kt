package com.lyra.app

import org.junit.jupiter.api.DisplayNameGenerator
import java.lang.reflect.Method
import java.util.*

class ReplaceCamelCase : DisplayNameGenerator.Standard() {
  override fun generateDisplayNameForMethod(testClass: Class<*>?, testMethod: Method): String {
    return replaceCapitals(testMethod.name)
  }

  private fun replaceCapitals(originalName: String): String {
    var name = originalName
    name = name.replace("([A-Z])".toRegex(), " $1")
    name = name.replace("([0-9]+)".toRegex(), " $1")
    name = name.lowercase(Locale.getDefault())
    return name
  }
}
