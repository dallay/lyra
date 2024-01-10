package com.lyra.common.domain

/**
 * Represents a base class for creating ID objects.
 *
 * @param value The value of the ID.
 * @param T The type of the ID value.
 *
 */
@Suppress("unused")
abstract class BaseId<T> protected constructor(val value: T) {
    init {
        require(value != null) { "The id cannot be null" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseId<*>) return false

        return value == other.value
    }

    override fun hashCode(): Int = value?.hashCode() ?: 0

    override fun toString(): String = value.toString()
}
