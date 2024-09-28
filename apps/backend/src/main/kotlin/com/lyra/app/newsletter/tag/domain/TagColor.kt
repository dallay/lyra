package com.lyra.app.newsletter.tag.domain

/**
 * Enum class representing different tag colors.
 *
 * @property value The string representation of the tag color.
 */
enum class TagColor(val value: String) {
    DEFAULT("default"),
    PURPLE("purple"),
    PINK("pink"),
    RED("red"),
    BLUE("blue"),
    YELLOW("yellow");

    companion object {
        /**
         * Converts a string value to a corresponding TagColor enum.
         *
         * @param value The string representation of the tag color.
         * @return The corresponding TagColor enum.
         * @throws IllegalArgumentException if the value does not match any TagColor.
         */
        fun fromString(value: String): TagColor {
            return when (value) {
                "default" -> DEFAULT
                "purple" -> PURPLE
                "pink" -> PINK
                "red" -> RED
                "blue" -> BLUE
                "yellow" -> YELLOW
                else -> throw IllegalArgumentException("Invalid tag color: $value")
            }
        }
    }
}
