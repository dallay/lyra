package com.lyra.app.authentication.infrastructure.http

import com.lyra.IntegrationTest
import com.lyra.app.authentication.domain.AccessToken
import com.lyra.app.authentication.infrastructure.cookie.AuthCookieBuilder
import com.lyra.app.config.InfrastructureTestContainers
import io.kotest.assertions.print.print
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.http.MediaType
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult

private const val ENDPOINT = "/api/refresh-token"

@Suppress("MultilineRawStringIndentation")
@AutoConfigureWebTestClient
@IntegrationTest
internal class RefreshTokenControllerIntegrationTest : InfrastructureTestContainers() {
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
    fun `should refresh token`() {
        webTestClient
            .mutateWith(csrf())
            .post()
            .uri(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .cookie(AuthCookieBuilder.REFRESH_TOKEN, accessToken?.refreshToken ?: "")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.token").isNotEmpty
            .jsonPath("$.expiresIn").isNotEmpty
            .jsonPath("$.refreshToken").isNotEmpty
            .jsonPath("$.refreshExpiresIn").isNotEmpty
            .jsonPath("$.tokenType").isNotEmpty
            .jsonPath("$.notBeforePolicy").isNotEmpty
            .jsonPath("$.sessionState").isNotEmpty
            .jsonPath("$.scope").isNotEmpty
            .consumeWith {
                println(it.responseBody?.print())
            }
    }
}
