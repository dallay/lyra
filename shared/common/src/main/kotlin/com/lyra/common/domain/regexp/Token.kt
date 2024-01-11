package com.lyra.common.domain.regexp

/**
 *
 * @created 10/1/24
 */

import java.util.regex.Pattern

abstract class Token(val value: String) {
    abstract fun convert(): String?
}

class EscapeToken(value: String) : Token(value) {
    override fun convert(): String = Pattern.quote(value)
}

class WildcardToken(value: String) : Token(value) {
    override fun convert(): String = ".*"
}

class WildcharToken(value: String) : Token(value) {
    override fun convert(): String = "."
}

class StringToken(value: String) : Token(value) {
    override fun convert(): String = Pattern.quote(value)
}
