package com.lyra.app.authentication.application.logout

import com.lyra.UnitTest
import com.lyra.app.authentication.domain.UserAuthenticatorLogout
import com.lyra.app.authentication.domain.error.LogoutFailedException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@UnitTest
internal class UserLogoutCommandHandlerTest {

    private lateinit var userLogoutService: UserLogoutService
    private val userAuthenticatorLogout: UserAuthenticatorLogout = mockk()
    private lateinit var userLogoutCommandHandler: UserLogoutCommandHandler

    @BeforeEach
    fun setUp() {
        coEvery { userAuthenticatorLogout.logout(any()) } returns Unit
        userLogoutService = UserLogoutService(userAuthenticatorLogout)
        userLogoutCommandHandler = UserLogoutCommandHandler(userLogoutService)
    }

    @Test
    fun `handle LogsOut User Successfully`(): Unit = runBlocking {
        val refreshToken = "valid_refresh_token"
        val command = UserLogoutCommand(refreshToken)

        userLogoutCommandHandler.handle(command)

        coVerify { userAuthenticatorLogout.logout(refreshToken) }
    }

    @Test
    fun `handle Throws Exception When LogoutFails`(): Unit = runBlocking {
        val refreshToken = "invalid_refresh_token"
        val command = UserLogoutCommand(refreshToken)

        coEvery {
            userAuthenticatorLogout.logout(refreshToken)
        } throws LogoutFailedException("Could not log out user", RuntimeException())

        assertThrows<LogoutFailedException> {
            userLogoutCommandHandler.handle(command)
        }

        coVerify { userAuthenticatorLogout.logout(refreshToken) }
    }
}
