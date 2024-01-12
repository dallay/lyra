package com.lyra.app.newsletter.infrastructure.http

import com.lyra.app.IntegrationTest
import java.util.*
import net.datafaker.Faker
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

private const val ENDPOINT = "/api/newsletter/subscribers"

@IntegrationTest
@AutoConfigureWebTestClient
internal class NewsletterSubscriberControllerIntegrationTest {
    private val faker = Faker()

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @BeforeEach
    fun setUp() {
        // Set Test container here
    }

    @Test
    fun `should subscribe a new subscriber`() {
        val request = generateRequest()
        webTestClient.put()
            .uri("$ENDPOINT/${UUID.randomUUID()}")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody().isEmpty
    }

    @Test
    fun `should not subscribe a new subscriber if email is invalid`() {
        val request = generateRequest(email = "invalid-email")
        webTestClient.put()
            .uri("$ENDPOINT/${UUID.randomUUID()}")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                request,
            )
            .exchange()
            .expectStatus().isBadRequest
            .expectBody().jsonPath("$.type")
            .isEqualTo("https://lyra.io/errors/bad-request")
            .jsonPath("$.title")
            .isEqualTo("Bad request")
            .jsonPath("$.status")
            .isEqualTo(400)
            .jsonPath("$.detail")
            .isEqualTo("The email <invalid-email> is not valid")
            .jsonPath("$.instance").isNotEmpty
    }

    @Suppress("MultilineRawStringIndentation")
    private fun generateRequest(
        email: String = faker.internet().emailAddress(),
        firstname: String = faker.name().firstName(),
        lastname: String = faker.name().lastName()
    ): String = """
      {
           "email": "$email",
           "firstname": "$firstname",
           "lastname": "$lastname"
       }
    """.trimIndent()
}
