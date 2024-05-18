package com.lyra.app.forms.infrastructure.http

import com.lyra.app.ControllerIntegrationTest
import com.lyra.app.IntegrationTest
import com.lyra.app.forms.FormStub
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql

private const val ENDPOINT = "/api/forms/update"

@IntegrationTest
internal class UpdateFormControllerIntegrationTest : ControllerIntegrationTest() {
    @BeforeEach
    fun setUp() {
        // Set Test container here
    }

    @Test
    @Sql(
        "/db/form/form.sql",
    )
    @Sql(
        "/db/form/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should update a new form`(): Unit = runBlocking {
        val id = "1659d4ae-402a-4172-bf8b-0a5c54255587"
        val request = FormStub.generateRequest()
        webTestClient.put()
            .uri("$ENDPOINT/$id")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .isEqualTo("Form updated successfully")
    }

    @Test
    fun `should return 404 when form is not found`(): Unit = runBlocking {
        val id = "a5533c80-61f4-4db2-9fb7-191caa94e2bc"
        val request = FormStub.generateRequest()
        webTestClient.put()
            .uri("$ENDPOINT/$id")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.type").isEqualTo("https://lyra.io/errors/entity-not-found")
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
