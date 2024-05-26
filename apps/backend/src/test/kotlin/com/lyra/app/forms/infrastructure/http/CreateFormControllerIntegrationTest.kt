package com.lyra.app.forms.infrastructure.http

import com.lyra.ControllerIntegrationTest
import com.lyra.app.forms.FormStub
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.test.context.jdbc.Sql

private const val ENDPOINT = "/api/forms"

internal class CreateFormControllerIntegrationTest : ControllerIntegrationTest() {
    @BeforeEach
    fun setUp() {
        // Set Test container here
    }

    @Test
    fun `should create a new form`() {
        val request = FormStub.generateRequest()
        val id = UUID.randomUUID().toString()
        webTestClient.mutateWith(csrf()).put()
            .uri("$ENDPOINT/$id")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody().isEmpty
    }

    @Test
    @Sql(
        "/db/form/form.sql",
    )
    @Sql(
        "/db/form/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should fail when the form already exists`() {
        val request = FormStub.generateRequest()
        val id = "1659d4ae-402a-4172-bf8b-0a5c54255587"
        webTestClient.mutateWith(csrf()).put()
            .uri("$ENDPOINT/$id")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.type").isEqualTo("https://lyra.com/errors/bad-request")
            .jsonPath("$.title").isEqualTo("Bad request")
            .jsonPath("$.status").isEqualTo(400)
            .jsonPath("$.detail").isEqualTo("Error creating form")
            .jsonPath("$.instance").isEqualTo("$ENDPOINT/$id")
            .jsonPath("$.errorCategory").isEqualTo("BAD_REQUEST")
            .jsonPath("$.timestamp").isNumber
    }
}
