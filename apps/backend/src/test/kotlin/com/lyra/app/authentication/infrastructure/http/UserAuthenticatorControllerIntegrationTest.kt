package com.lyra.app.authentication.infrastructure.http

import com.lyra.app.CredentialGenerator
import com.lyra.app.config.InfrastructureTestContainers
import com.lyra.app.users.domain.User
import com.lyra.app.users.domain.UserCreator
import com.lyra.common.domain.vo.credential.Credential
import io.kotest.assertions.print.print
import java.util.*
import kotlinx.coroutines.runBlocking
import net.datafaker.Faker
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.http.MediaType
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.test.web.reactive.server.WebTestClient

private const val ENDPOINT = "/api/login"

private const val TITLE = "User authentication failed"

private const val DETAIL = "Invalid account. User probably hasn't verified email."

private const val ERROR_CATEGORY = "AUTHENTICATION"

@Suppress("MultilineRawStringIndentation")
@AutoConfigureWebTestClient
internal class UserAuthenticatorControllerIntegrationTest : InfrastructureTestContainers() {
    // this user is created by default in Keycloak container (see demo-realm-test.json)
    private val email = "john.doe@lyra.com"
    private val username = "john.doe"
    private val password = "S3cr3tP@ssw0rd*123"

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var userCreator: UserCreator
    private val faker = Faker()

    @BeforeEach
    fun setUp() {
        startInfrastructure()
    }

    @Test
    fun `should not authenticate a user without csrf token`() {
        webTestClient.post()
            .uri(ENDPOINT)
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
            .expectStatus().isForbidden
    }

    @Test
    fun `should authenticate a user by email`() {
        webTestClient
            .mutateWith(csrf())
            .post()
            .uri(ENDPOINT)
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

    @Test
    fun `should authenticate a user by username`() {
        webTestClient
            .mutateWith(csrf())
            .post()
            .uri(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                {
                    "username": "$username",
                    "password": "$password"
                }
                """.trimIndent(),
            )
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

    @Test
    fun `should not authenticate a user with invalid credentials`() {
        webTestClient
            .mutateWith(csrf())
            .post()
            .uri(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                {
                    "username": "$email",
                    "password": "${password}invalidPassword"
                }
                """.trimIndent(),
            )
            .exchange()
            .expectStatus().isUnauthorized
            .expectBody()
            .jsonPath("$.title").isEqualTo(TITLE)
            .jsonPath("$.detail").isEqualTo(DETAIL)
            .jsonPath("$.instance").isEqualTo(ENDPOINT)
            .jsonPath("$.errorCategory").isEqualTo(ERROR_CATEGORY)
            .jsonPath("$.timestamp").isNotEmpty
    }

    @Test
    fun `should not authenticate a user with invalid username`() {
        webTestClient
            .mutateWith(csrf())
            .post()
            .uri(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                {
                    "username": "${username}invalidUsername",
                    "password": "$password"
                }
                """.trimIndent(),
            )
            .exchange()
            .expectStatus().isUnauthorized
            .expectBody()
            .jsonPath("$.title").isEqualTo(TITLE)
            .jsonPath("$.detail").isEqualTo(DETAIL)
            .jsonPath("$.instance").isEqualTo(ENDPOINT)
            .jsonPath("$.errorCategory").isEqualTo(ERROR_CATEGORY)
            .jsonPath("$.timestamp").isNotEmpty
    }

    @Test
    fun `should throw exception when user is not verified`(): Unit = runBlocking {
        val randomEmail = "yap@example.com"
        val randomPassword = faker.internet().password(8, 100, true, true, true) + "1Aa@"
        val user = createUser(email = randomEmail, password = randomPassword)
            .also {
                println("\uD83E\uDDEA TEST: User create for test purpose: $it")
            }
        webTestClient
            .mutateWith(csrf())
            .post()
            .uri(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                {
                    "username": "${user.email.value}",
                    "password": "$randomPassword"
                }
                """.trimIndent(),
            )
            .exchange()
            .expectStatus().isUnauthorized
            .expectBody()
            .jsonPath("$.title").isEqualTo(TITLE)
            .jsonPath("$.detail").isEqualTo(DETAIL)
            .jsonPath("$.instance").isEqualTo(ENDPOINT)
            .jsonPath("$.errorCategory").isEqualTo(ERROR_CATEGORY)
            .jsonPath("$.timestamp").isNotEmpty
    }

    private suspend fun createUser(
        email: String = faker.internet().emailAddress(),
        password: String = Credential.generateRandomCredentialPassword(),
        firstName: String = faker.name().firstName(),
        lastName: String = faker.name().lastName()
    ): User {
        val user = User(
            id = UUID.randomUUID(),
            email = email,
            credentials = mutableListOf(CredentialGenerator.generate(password)),
            firstName = firstName,
            lastName = lastName,
        )
        return userCreator.create(user)
    }
}
