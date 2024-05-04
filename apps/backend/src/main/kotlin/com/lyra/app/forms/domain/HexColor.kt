package com.lyra.app.forms.domain

import com.lyra.common.domain.BaseValidateValueObject

/**
 * Value object for hexadecimal color codes that are validated when created
 */
data class HexColor(var hex: String) : BaseValidateValueObject<String>(hex) {

    init {
        hex = if (hex.startsWith("#")) hex else "#$hex"
    }
    /**
     * Validates the value of the value object
     * @param value the value to validate
     */
    override fun validate(value: String) {
        require(regex.matches(value)) { "Invalid hexadecimal color code: $value" }
    }

    /**
     * Returns the string representation of the value object
     * @return the string representation of the value object
     */
    override fun toString(): String = if (value.startsWith("#")) value else "#$value"
    companion object {
        val regex = Regex("^#?([0-9a-f]{6}|[0-9a-f]{3})$", RegexOption.IGNORE_CASE)
    }
}
