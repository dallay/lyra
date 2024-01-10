package com.lyra.common.domain

import java.io.Serializable

/**
 * Base class for value objects that are validated when created
 *
 * @created 2/7/23
 */
@Suppress("SerialVersionUIDInSerializableClass")
abstract class BaseValueObject<T> protected constructor(val value: T) : Serializable {

    /**
     * Compares two value objects by their value
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseValueObject<*>) return false

        if (value != other.value) return false

        return true
    }

    /**
     * Returns the hash code of the value object
     */
    override fun hashCode(): Int = value?.hashCode() ?: 0

    /**
     * Returns the string representation of the value object
     */
    override fun toString(): String = value.toString()

    @Suppress("serialVersionUID")
    companion object {
        private const val serialVersionUID = -68L
    }
}
