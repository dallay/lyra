package com.lyra.app.authentication.application

import com.lyra.app.authentication.application.query.RefreshTokenQuery
import com.lyra.app.authentication.domain.RefreshTokenManager
import io.kotest.common.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class RefreshTokenQueryHandlerTest {
    private val refreshTokenManager: RefreshTokenManager = InMemoryUserAuthenticatorManager()
    private val refreshTokenQueryHandler: RefreshTokenQueryHandler = RefreshTokenQueryHandler(
        refreshTokenManager,
    )
    private val refreshTokenQuery = RefreshTokenQuery(refreshToken = "refreshToken")

    @Test
    fun `handle refresh token`() = runBlocking {
        val accessToken = refreshTokenQueryHandler.handle(refreshTokenQuery)
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
}
