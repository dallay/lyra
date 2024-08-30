package com.lyra.app.users.infrastructure.http

import com.lyra.app.config.InfrastructureTestContainers
import com.lyra.common.domain.vo.credential.Credential
import io.kotest.assertions.print.print
import net.datafaker.Faker
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.http.MediaType
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.test.web.reactive.server.WebTestClient

private const val ENDPOINT = "/api/register"
@Suppress("MultilineRawStringIndentation")
@AutoConfigureWebTestClient
internal class UserRegisterControllerIntegrationTest : InfrastructureTestContainers() {
    private val faker = Faker()

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @BeforeEach
    fun setUp() {
        startInfrastructure()
    }

    @Test
    fun `should not register a new user without csrf token`() {
        webTestClient.post()
            .uri(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                {
                    "email": "${faker.internet().emailAddress()}",
                    "password": "${Credential.generateRandomCredentialPassword()}",
                    "firstname": "${faker.name().firstName()}",
                    "lastname": "${faker.name().lastName()}"
                }
                """.trimIndent(),
            )
            .exchange()
            .expectStatus().isForbidden
    }

    @Test
    fun `should register a new user`() {
        webTestClient
            .mutateWith(csrf())
            .post()
            .uri(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                {
                    "email": "${faker.internet().emailAddress()}",
                    "password": "${Credential.generateRandomCredentialPassword()}",
                    "firstname": "${faker.name().firstName()}",
                    "lastname": "${faker.name().lastName()}"
                }
                """.trimIndent(),
            )
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .consumeWith {
                println(it.responseBody?.print())
            }
    }

    @Test
    fun `should not register a new user with an existing email`() {
        val email = faker.internet().emailAddress()
        val password = Credential.generateRandomCredentialPassword()
        val firstname = faker.name().firstName()
        val lastname = faker.name().lastName()

        webTestClient
            .mutateWith(csrf())
            .post()
            .uri(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                {
                    "email": "$email",
                    "password": "$password",
                    "firstname": "$firstname",
                    "lastname": "$lastname"
                }
                """.trimIndent(),
            )
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .consumeWith {
                println(it.responseBody?.print())
            }

        webTestClient
            .mutateWith(csrf())
            .post()
            .uri(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                {
                    "email": "$email",
                    "password": "${Credential.generateRandomCredentialPassword()}",
                    "firstname": "${faker.name().firstName()}",
                    "lastname": "${faker.name().lastName()}"
                }
                """.trimIndent(),
            )
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .consumeWith {
                println(it.responseBody?.print())
            }
    }

    @Test
    fun `should not register a new user with an invalid email`() {
        webTestClient
            .mutateWith(csrf())
            .post()
            .uri(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                {
                    "email": "invalid-email",
                    "password": "${Credential.generateRandomCredentialPassword()}",
                    "firstname": "${faker.name().firstName()}",
                    "lastname": "${faker.name().lastName()}"
                }
                """.trimIndent(),
            )
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .consumeWith {
                println(it.responseBody?.print())
            }
    }

    @Test
    fun `should not register a new user with an invalid password`() {
        val invalidPasswords = listOf("invalid-password", "1234", "12345678", "ashihweitwjjw", "WEAKPASSWORD1")
        invalidPasswords.forEach {
            webTestClient
                .mutateWith(csrf())
                .post()
                .uri(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(
                    """
                    {
                        "email": "${faker.internet().emailAddress()}",
                        "password": "$it",
                        "firstname": "${faker.name().firstName()}",
                        "lastname": "${faker.name().lastName()}"
                    }
                    """.trimIndent(),
                )
                .exchange()
                .expectStatus().isBadRequest
                .expectBody()
                .consumeWith {
                    println(it.responseBody?.print())
                }
        }
    }

    @Test
    fun `should not register a new user with an invalid firstname`() {
        webTestClient
            .mutateWith(csrf())
            .post()
            .uri(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                {
                    "email": "${faker.internet().emailAddress()}",
                    "password": "${Credential.generateRandomCredentialPassword()}",
                    "firstname": "a",
                    "lastname": "${faker.name().lastName()}"
                }
                """.trimIndent(),
            )
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .consumeWith {
                println(it.responseBody?.print())
            }
    }

    @Test
    fun `should not register a new user with an invalid lastname`() {
        webTestClient
            .mutateWith(csrf())
            .post()
            .uri(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                {
                    "email": "${faker.internet().emailAddress()}",
                    "password": "${Credential.generateRandomCredentialPassword()}",
                    "firstname": "${faker.name().firstName()}",
                    "lastname": "a"
                }
                """.trimIndent(),
            )
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .consumeWith {
                println(it.responseBody?.print())
            }
    }
}
