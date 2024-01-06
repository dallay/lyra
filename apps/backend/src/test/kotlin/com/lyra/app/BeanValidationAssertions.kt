package com.lyra.app

import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.assertj.core.api.Assertions.assertThat

@Suppress("unused")
object BeanValidationAssertions {
    private val validator: Validator = Validation.buildDefaultValidatorFactory().validator

    fun <T> assertThatBean(bean: T): BeanAsserter<T> = BeanAsserter(bean)

    class BeanAsserter<T>(bean: T) {
        private val violations: Set<ConstraintViolation<T>> = validator.validate(bean)

        fun isValid(): BeanAsserter<T> {
            assertThat(violations).isEmpty()
            return this
        }

        fun hasInvalidProperty(property: String): InvalidPropertyAsserter<T> {
            require(property.isNotBlank()) { "property must not be empty" }
            val validation = violations
                .stream()
                .filter { it.propertyPath.toString() == property }
                .findFirst()
                .orElseThrow { AssertionError("Property $property must be invalid and wasn't") }
            return InvalidPropertyAsserter(this, validation)
        }
    }

    class InvalidPropertyAsserter<T>(
        private val beanAsserter: BeanAsserter<T>,
        private val violation: ConstraintViolation<T>
    ) {
        fun withMessage(message: String): InvalidPropertyAsserter<T> {
            require(message.isNotBlank()) { "message must not be empty" }
            assertThat(violation.message).isEqualTo(message)
            return this
        }

        fun withParameter(parameter: String, value: Any): InvalidPropertyAsserter<T> {
            require(parameter.isNotBlank()) { "parameter must not be empty" }
            assertThat(violation.constraintDescriptor.attributes).containsEntry(parameter, value)
            return this
        }

        fun and(): BeanAsserter<T> = beanAsserter
    }
}
