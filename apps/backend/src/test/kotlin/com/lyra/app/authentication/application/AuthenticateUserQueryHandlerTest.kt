package com.lyra.app.authentication.application

import com.lyra.UnitTest
import com.lyra.app.authentication.application.query.AuthenticateUserQuery
import com.lyra.app.authentication.domain.UserAuthenticationException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.core.context.SecurityContextHolder

private const val PASSWORD = "My5up3rS3cr3tP@ssw0rd"

private const val USER = "user"

@UnitTest
class AuthenticateUserQueryHandlerTest {
    private val inMemoryUserAuthenticatorManager = InMemoryUserAuthenticatorManager()
    private val userAuthenticatorService: UserAuthenticatorService = UserAuthenticatorService(
        inMemoryUserAuthenticatorManager,
    )
    private val authenticateUserQueryHandler: AuthenticateUserQueryHandler = AuthenticateUserQueryHandler(
        userAuthenticatorService,
    )

    @BeforeEach
    fun setUp() {
        SecurityContextHolder.clearContext()
        inMemoryUserAuthenticatorManager.addUser(USER, PASSWORD)
    }

    @AfterEach
    fun tearDown() {
        SecurityContextHolder.clearContext()
        inMemoryUserAuthenticatorManager.clear()
    }

    @Test
    fun `should authenticate a user`() = runTest {
        val accessToken = authenticateUserQueryHandler.handle(
            AuthenticateUserQuery(
                username = USER,
                password = PASSWORD,
            ),
        )
        assertNotNull(accessToken)
        assertEquals("token", accessToken.token)
        assertEquals(1, accessToken.expiresIn)
        assertEquals("refreshToken", accessToken.refreshToken)
        assertEquals(1, accessToken.refreshExpiresIn)
        assertEquals("type", accessToken.tokenType)
        assertEquals(1, accessToken.notBeforePolicy)
        assertEquals("81945a53-7bfa-4347-9321-b46c2a2a736d", accessToken.sessionState)
        assertEquals("email profile", accessToken.scope)
    }

    @Test
    fun `should throw exception when user is not found`() = runTest {
        val exception = assertThrows(UserAuthenticationException::class.java) {
            runBlocking {
                authenticateUserQueryHandler.handle(
                    AuthenticateUserQuery(
                        username = "unknown",
                        password = PASSWORD,
                    ),
                )
            }
        }
        assertEquals("Invalid account. User probably hasn't verified email.", exception.message)
    }
}
