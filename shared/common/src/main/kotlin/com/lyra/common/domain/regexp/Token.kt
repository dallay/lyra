package com.lyra.common.domain.regexp

import java.util.regex.Pattern

/**
 * Abstract class representing a token in a SQL LIKE pattern.
 *
 * @property value The string value of the token.
 */
abstract class Token(val value: String) {
    /**
     * Converts the token to its equivalent regular expression.
     *
     * @return The regular expression string.
     */
    abstract fun convert(): String?
}

/**
 * Token representing an escaped character in a SQL LIKE pattern.
 *
 * @param value The escaped character.
 */
class EscapeToken(value: String) : Token(value) {
    /**
     * Converts the escaped character to its equivalent regular expression.
     *
     * @return The quoted string for the escaped character.
     */
    override fun convert(): String = Pattern.quote(value)
}

/**
 * Token representing a wildcard '%' in a SQL LIKE pattern.
 *
 * @param value The wildcard character.
 */
class WildcardToken(value: String) : Token(value) {
    /**
     * Converts the wildcard '%' to its equivalent regular expression.
     *
     * @return The regular expression for the wildcard.
     */
    override fun convert(): String = ".*"
}

/**
 * Token representing a wildcard '_' in a SQL LIKE pattern.
 *
 * @param value The wildcard character.
 */
class WildcharToken(value: String) : Token(value) {
    /**
     * Converts the wildcard '_' to its equivalent regular expression.
     *
     * @return The regular expression for the wildcard.
     */
    override fun convert(): String = "."
}

/**
 * Token representing a regular string in a SQL LIKE pattern.
 *
 * @param value The string value.
 */
class StringToken(value: String) : Token(value) {
    /**
     * Converts the string to its equivalent regular expression.
     *
     * @return The quoted string for the regular string.
     */
    override fun convert(): String = Pattern.quote(value)
}
