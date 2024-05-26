package com.lyra.common.domain.vo.credential

import com.lyra.common.domain.BaseId
import com.lyra.common.domain.BaseValidateValueObject
import java.util.*

/**
 * Credential representation in the domain layer of the application that is used to authenticate a user
 * @created 2/7/23
 */
data class Credential(
    val id: CredentialId,
    val credentialValue: String,
    val type: CredentialType = CredentialType.PASSWORD
) : BaseValidateValueObject<String>(credentialValue) {

    /**
     * Validates the value of the value object
     * @param value the value to validate
     */
    override fun validate(value: String) {
        if (value.isBlank()) {
            throw CredentialException("Credential value cannot be blank")
        }
        if (value.length < MIN_LENGTH) {
            throw CredentialException("Credential value must be at least $MIN_LENGTH characters")
        }
        // Password must have at least one number, one uppercase, one lowercase and one special character
        if (!value.any { charNumbers.contains(it) }) {
            throw CredentialException("The password must have at least one number")
        }
        if (!value.any { charUppercase.contains(it) }) {
            throw CredentialException("The password must have at least one uppercase character")
        }
        if (!value.any { charLowercase.contains(it) }) {
            throw CredentialException("The password must have at least one lowercase character")
        }
        if (!value.any { charSpecial.contains(it) }) {
            throw CredentialException("The password must have at least one special character")
        }
    }

    companion object {
        const val MIN_LENGTH = 8
        private val charNumbers = '0'..'9'
        private val charUppercase = 'A'..'Z'
        private val charLowercase = 'a'..'z'
        private val charSpecial = "!@#$%^&*()_+{}|:<>?".toList()
        private val charset = charLowercase + charUppercase + charNumbers

        /**
         * Generates a random password with the following rules:
         * - Must have at least one number, one uppercase, one lowercase and one special character
         * - Must have at least 8 characters
         * @return the generated password
         * @see MIN_LENGTH the minimum length of the password
         * @see charNumbers the list of numbers
         * @see charUppercase the list of uppercase characters
         * @see charLowercase the list of lowercase characters
         * @see charSpecial the list of special characters
         * @see charset the list of all characters
         * @see Random the random generator
         * @see Random.nextInt to generate a random number
         * @see List.random to get a random element from a list
         * @see List.shuffled to shuffle the list
         * @see List.joinToString to join the list into a string
         * @see List.toMutableList to convert the list into a mutable list
         */
        fun generateRandomCredentialPassword(): String {
            val length = MIN_LENGTH + Random().nextInt(MIN_LENGTH)
            // Must have at least one number, one uppercase, one lowercase and one special character
            val password = (1..length)
                .map { charset.random() }
                .toMutableList()
                .apply {
                    add(charNumbers.random())
                    add(charUppercase.random())
                    add(charLowercase.random())
                    add(charSpecial.random())
                }
                .shuffled()
                .joinToString("")
            return password
        }

        /**
         * Creates a new credential with the given value and type
         * @param credentialValue the value of the credential
         * @param type the type of the credential (default is [CredentialType.PASSWORD])
         * @return the created credential with the given value and type and a random id generated with [UUID.randomUUID]
         * @see UUID.randomUUID for the id generation
         * @see CredentialId for the id type
         * @see Credential for the credential type
         * @see CredentialType for the available types
         */
        fun create(
            credentialValue: String,
            type: CredentialType = CredentialType.PASSWORD
        ): Credential = Credential(CredentialId(UUID.randomUUID()), credentialValue, type)
    }
}

/**
 * Credential id representation in the domain layer of the application that is used to identify a credential
 * @see BaseId for the base id class
 * @see UUID for the id type
 * @see Credential for the credential type
 * @see CredentialId for the id type
 */
class CredentialId(id: UUID) : BaseId<UUID>(id)

/**
 * Credential type representation in the domain layer of the application that is used to identify the type of credential
 * @see Credential for the credential type
 */
enum class CredentialType {
    PASSWORD,
    TOTP,
    SECRET
}
