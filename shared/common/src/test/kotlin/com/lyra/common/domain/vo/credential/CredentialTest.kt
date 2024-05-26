package com.lyra.common.domain.vo.credential

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class CredentialTest {
    private val strongPassword = "Th1sIsA$5tr0ngP@ssw0rd"

    @Test
    fun `should create a credential`() {
        val credential = Credential.create(strongPassword)
        assertThat(credential).isNotNull
        assertThat(credential.value).isNotBlank()
        assertEquals(credential.value, strongPassword)
        assertEquals(credential.type, CredentialType.PASSWORD)
    }

    @Test
    fun `should not create a credential with a weak password must have at least one number`() {
        val assertThrows = assertThrows(CredentialException::class.java) {
            Credential.create("weakpassword")
        }
        assertThat(assertThrows.message).isEqualTo("The password must have at least one number")
    }

    @Test
    fun `should not create a credential with a weak password must have at least one uppercase`() {
        val assertThrows = assertThrows(CredentialException::class.java) {
            Credential.create("weakpassword1")
        }
        assertThat(assertThrows.message).isEqualTo("The password must have at least one uppercase character")
    }

    @Test
    fun `should not create a credential with a weak password must have at least one lowercase`() {
        val assertThrows = assertThrows(CredentialException::class.java) {
            Credential.create("WEAKPASSWORD1")
        }
        assertThat(assertThrows.message).isEqualTo("The password must have at least one lowercase character")
    }

    @Test
    fun `should not create a credential with a weak password must have at least one special character`() {
        val assertThrows = assertThrows(CredentialException::class.java) {
            Credential.create("Weakpassword1")
        }
        assertThat(assertThrows.message).isEqualTo("The password must have at least one special character")
    }

    @Test
    fun `should not create a credential with a empty password`() {
        val assertThrows = assertThrows(CredentialException::class.java) {
            Credential.create("")
        }
        assertThat(assertThrows.message).isEqualTo("Credential value cannot be blank")
    }

    @Test
    fun `should not create a credential with a blank password`() {
        val assertThrows = assertThrows(CredentialException::class.java) {
            Credential.create(" ")
        }
        assertThat(assertThrows.message).isEqualTo("Credential value cannot be blank")
    }

    @Test
    fun `should not create a credential with less than 8 characters`() {
        val assertThrows = assertThrows(CredentialException::class.java) {
            Credential.create("Weak@1")
        }
        assertThat(assertThrows.message).isEqualTo("Credential value must be at least 8 characters")
    }

    @Test
    fun `compare two credentials`() {
        val credential = Credential.create(strongPassword)
        val credential2 = Credential.create(strongPassword)
        assertThat(credential).isNotEqualTo(credential2)
    }

    @Test
    fun `should generate a random password`() {
        val credentialPassword = Credential.generateRandomCredentialPassword()
        assertThat(credentialPassword).isNotBlank()
        assertThat(credentialPassword.length).isGreaterThanOrEqualTo(Credential.MIN_LENGTH)
        val credential = Credential.create(credentialPassword)
        assertThat(credential).isNotNull
        assertThat(credential.value).isNotBlank()
        assertEquals(credential.value, credentialPassword)
    }
}
