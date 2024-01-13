package com.lyra.common.domain

/**
 * Base class for value objects that are validated when created
 *
 * @created 2/7/23
 */
abstract class BaseValidateValueObject<T> protected constructor(value: T) :
    BaseValueObject<T>(value) {
    init {
        this.validate(value)
    }

    /**
     * Validates the value of the value object
     * @param value the value to validate
     */
    abstract fun validate(value: T)
}
