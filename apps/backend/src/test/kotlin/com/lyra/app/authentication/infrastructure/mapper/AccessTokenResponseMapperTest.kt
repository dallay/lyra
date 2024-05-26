package com.lyra.app.authentication.infrastructure.mapper

import com.lyra.app.authentication.infrastructure.mapper.AccessTokenResponseMapper.toAccessToken
import net.datafaker.Faker
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.keycloak.representations.AccessTokenResponse

internal class AccessTokenResponseMapperTest {

    private lateinit var tokenResponse: AccessTokenResponse
    private val faker = Faker()

    @BeforeEach
    fun setUp() {
        tokenResponse = AccessTokenResponse()
        tokenResponse.token = faker.lorem().characters(10)
        tokenResponse.expiresIn = faker.number().numberBetween(1L, 100L)
        tokenResponse.refreshToken = faker.lorem().characters(10)
        tokenResponse.refreshExpiresIn = faker.number().numberBetween(1L, 100L)
        tokenResponse.tokenType = faker.lorem().characters(10)
        tokenResponse.notBeforePolicy = faker.number().numberBetween(1, 100)
        tokenResponse.sessionState = faker.lorem().characters(10)
        tokenResponse.scope = faker.lorem().characters(10)
    }

    @Test
    fun `toAccessToken should map token`() {
        val accessToken = tokenResponse.toAccessToken()
        assertEquals(tokenResponse.token, accessToken.token)
        assertEquals(tokenResponse.expiresIn, accessToken.expiresIn)
        assertEquals(tokenResponse.refreshToken, accessToken.refreshToken)
        assertEquals(tokenResponse.refreshExpiresIn, accessToken.refreshExpiresIn)
        assertEquals(tokenResponse.tokenType, accessToken.tokenType)
        assertEquals(tokenResponse.notBeforePolicy, accessToken.notBeforePolicy)
        assertEquals(tokenResponse.sessionState, accessToken.sessionState)
        assertEquals(tokenResponse.scope, accessToken.scope)
    }
}
