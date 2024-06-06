package com.lyra.app.forms.infrastructure.http

import com.lyra.ControllerIntegrationTest
import com.lyra.IntegrationTest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.test.context.jdbc.Sql

private const val ENDPOINT = "/api/forms"

@IntegrationTest
internal class FindFormControllerIntegrationTest : ControllerIntegrationTest() {

    @Test
    @Sql(
        "/db/form/form.sql",
    )
    @Sql(
        "/db/form/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should return form when form is found`(): Unit = runBlocking {
        val id = "1659d4ae-402a-4172-bf8b-0a5c54255587"
        webTestClient.get()
            .uri("$ENDPOINT/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id)
            .jsonPath("$.name").isEqualTo("Programming newsletter v1")
            .jsonPath("$.header").isEqualTo("Juan's Newsletter")
            .jsonPath("$.description").isEqualTo("\uD83D\uDFE2 Some description \uD83D\uDD34")
            .jsonPath("$.inputPlaceholder").isEqualTo("Enter your email")
            .jsonPath("$.buttonText").isEqualTo("Subscribe")
            .jsonPath("$.buttonColor").isEqualTo("#2C81E5")
            .jsonPath("$.backgroundColor").isEqualTo("#DFD150")
            .jsonPath("$.textColor").isEqualTo("#222222")
            .jsonPath("$.buttonTextColor").isEqualTo("#FFFFFF")
            .jsonPath("$.createdAt").isNotEmpty
            .jsonPath("$.updatedAt").isNotEmpty
            .consumeWith { response ->
                println(response)
            }
    }

    @Test
    fun `should return 404 when form is not found`(): Unit = runBlocking {
        val id = "94be1a32-cf2e-4dfc-892d-bdd8ac7ad354"
        webTestClient.get()
            .uri("$ENDPOINT/$id")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.type").isEqualTo("https://lyra.com/errors/entity-not-found")
            .jsonPath("$.title").isEqualTo("Entity not found")
            .jsonPath("$.status").isEqualTo(404)
            .jsonPath("$.detail").isEqualTo("Form not found")
            .jsonPath("$.instance").isEqualTo("$ENDPOINT/$id")
            .jsonPath("$.errorCategory").isEqualTo("NOT_FOUND")
            .jsonPath("$.timestamp").isNotEmpty
            .consumeWith { response ->
                println(response)
            }
    }
}
