package com.lyra.app.authentication.domain

import com.lyra.UnitTest
import com.lyra.app.authentication.domain.error.UsernameException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

@UnitTest
internal class UsernameTest {

    @Test
    fun `should get empty username from blank username`() {
        assertThat(Username.of(" ")).isEmpty()
    }

    @Test
    fun `should get username from actual username`() {
        assertThat(Username.of("user")).contains(Username("user"))
    }

    @Test
    fun `should get username`() {
        assertThat(Username("user").username).isEqualTo("user")
    }

    @Test
    fun `should get empty username from 2 char username`() {
        assertThat(Username.of("ab")).isEmpty()
    }

    @Test
    fun `should get empty username from 101 char username`() {
        assertThat(Username.of("a".repeat(101))).isEmpty()
    }

    @Test
    fun `should throw exception when username is blank`() {
        val instanceOf = assertThatThrownBy { Username(" ") }.isInstanceOf(UsernameException::class.java)
        instanceOf.hasMessage("Username cannot be blank")
    }

    @Test
    fun `should throw exception when username is less than 3 characters`() {
        val instanceOf = assertThatThrownBy { Username("ab") }.isInstanceOf(UsernameException::class.java)
        instanceOf.hasMessage("Username must be between 3 and 100 characters")
    }

    @Test
    fun `should throw exception when username is more than 100 characters`() {
        val instanceOf = assertThatThrownBy { Username("a".repeat(101)) }.isInstanceOf(UsernameException::class.java)
        instanceOf.hasMessage("Username must be between 3 and 100 characters")
    }
}
