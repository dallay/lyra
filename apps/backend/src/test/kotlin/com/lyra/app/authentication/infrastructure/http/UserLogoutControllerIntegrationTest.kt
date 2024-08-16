package com.lyra.app.authentication.infrastructure.http

import com.lyra.IntegrationTest
import com.lyra.app.authentication.domain.AccessToken
import com.lyra.app.authentication.infrastructure.cookie.AuthCookieBuilder
import com.lyra.app.config.InfrastructureTestContainers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.http.MediaType
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult

private const val ENDPOINT = "/api/logout"

@Suppress("MultilineRawStringIndentation")
@AutoConfigureWebTestClient
@IntegrationTest
internal class UserLogoutControllerIntegrationTest : InfrastructureTestContainers() {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    private val email = "john.doe@lyra.com"
    private val password = "S3cr3tP@ssw0rd*123"
    private var accessToken: AccessToken? = null

    @BeforeEach
    fun setUp() {
        startInfrastructure()
        val returnResult = webTestClient
            .mutateWith(csrf())
            .post()
            .uri("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                {
                    "username": "$email",
                    "password": "$password"
                }
                """.trimIndent(),
            )
            .exchange()
            .returnResult<AccessToken>()
        accessToken = returnResult.responseBody.blockFirst()
    }

    @Test
    fun `logout user successfully`() {

        webTestClient.mutateWith(csrf()).post()
            .uri(ENDPOINT)
            .cookie(AuthCookieBuilder.ACCESS_TOKEN, accessToken?.token ?: "")
            .cookie(AuthCookieBuilder.REFRESH_TOKEN, accessToken?.refreshToken ?: "")
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun `logout user with missing cookies`() {

        webTestClient.mutateWith(csrf()).post()
            .uri(ENDPOINT)
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun `logout user with missing refresh token`() {

        webTestClient.mutateWith(csrf()).post()
            .uri(ENDPOINT)
            .cookie(AuthCookieBuilder.ACCESS_TOKEN, accessToken?.token ?: "")
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun `logout user with wrong refresh token`() {

        webTestClient.mutateWith(csrf()).post()
            .uri(ENDPOINT)
            .cookie(AuthCookieBuilder.ACCESS_TOKEN, accessToken?.token ?: "")
            .cookie(AuthCookieBuilder.REFRESH_TOKEN, "wrong")
            .exchange()
            .expectStatus().isBadRequest
    }
}
