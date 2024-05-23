package com.lyra.common.domain.vo.email

import com.lyra.common.domain.BaseValidateValueObject
import com.lyra.common.domain.error.EmailNotValidException

private const val EMAIL_LEN = 255

/**
 * Email value object
 * @param email value
 * @throws [EmailNotValidException] if email is not valid
 * @see [BaseValidateValueObject] validate value object
 * @see [BaseValueObject] base value object
 * @see [EmailNotValidException] email not valid exception
 */
data class Email(val email: String) : BaseValidateValueObject<String>(email) {

    /**
     * Validate email value object with regex
     * @param value email value
     * @throws EmailNotValidException if email is not valid
     */
    override fun validate(value: String) {
        if (value.length > EMAIL_LEN || !value.matches(REGEX.toRegex())) {
            throw EmailNotValidException(value)
        }
    }
    companion object {
        @Suppress("MaxLineLength")
        private const val REGEX =
            "^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$"
    }
}
