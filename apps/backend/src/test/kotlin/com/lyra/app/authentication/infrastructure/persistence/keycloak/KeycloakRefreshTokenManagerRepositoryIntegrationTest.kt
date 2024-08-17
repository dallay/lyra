package com.lyra.app.authentication.infrastructure.persistence.keycloak

import com.lyra.IntegrationTest
import com.lyra.app.authentication.domain.RefreshToken
import com.lyra.app.authentication.domain.RefreshTokenManager
import com.lyra.app.authentication.domain.UserRefreshTokenException
import com.lyra.app.config.InfrastructureTestContainers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@IntegrationTest
internal class KeycloakRefreshTokenManagerRepositoryIntegrationTest : InfrastructureTestContainers() {

    @Autowired
    private lateinit var refreshTokenManager: RefreshTokenManager

    @BeforeEach
    fun setUp() {
        startInfrastructure()
    }

    @Test
    fun `should refresh access token`(): Unit = runBlocking {
        val accessToken = getAccessToken()
        val refreshToken = RefreshToken(accessToken?.refreshToken ?: "fake refresh token")
        val newAccessToken = refreshTokenManager.refresh(refreshToken)
        assertNotNull(newAccessToken)
        assertNotNull(newAccessToken.token)
        assertNotNull(newAccessToken.refreshToken)
        assertNotNull(newAccessToken.expiresIn)
        assertNotNull(newAccessToken.refreshExpiresIn)
        assertNotNull(newAccessToken.tokenType)
        assertNotNull(newAccessToken.scope)
        assertNotNull(newAccessToken.notBeforePolicy)
        assertNotNull(newAccessToken.sessionState)
        assertNotEquals(accessToken?.token, newAccessToken.token)
        assertNotEquals(accessToken?.refreshToken, newAccessToken.refreshToken)
    }

    @Test
    fun `should return exception when refresh access token`(): Unit = runBlocking {
        val refreshToken = RefreshToken("refreshToken")
        val exception = assertThrows(UserRefreshTokenException::class.java) {
            runBlocking { refreshTokenManager.refresh(refreshToken) }
        }
        assertNotNull(exception)
        assertEquals("Could not refresh access token", exception.message)
    }
}
