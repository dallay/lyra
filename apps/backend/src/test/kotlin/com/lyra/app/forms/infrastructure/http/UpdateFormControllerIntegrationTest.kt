package com.lyra.app.forms.infrastructure.http

import com.lyra.ControllerIntegrationTest
import com.lyra.app.forms.FormStub
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.test.context.jdbc.Sql

internal class UpdateFormControllerIntegrationTest : ControllerIntegrationTest() {
    private val organizationId = "7a27728a-8ef3-4070-b615-1d5ddf9a7863"

    @Test
    @Sql(
        "/db/form/form.sql",
    )
    @Sql(
        "/db/form/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should update a new form`(): Unit = runBlocking {
        val formId = "1659d4ae-402a-4172-bf8b-0a5c54255587"
        val request = FormStub.generateRequest()
        webTestClient.mutateWith(csrf()).put()
            .uri("/api/organization/$organizationId/form/$formId/update")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .isEqualTo("Form updated successfully")
    }

    @Test
    @Sql(
        "/db/organization/organization.sql",
    )
    @Sql(
        "/db/organization/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should return 404 when form is not found`(): Unit = runBlocking {
        val id = "a5533c80-61f4-4db2-9fb7-191caa94e2bc"
        val request = FormStub.generateRequest()
        webTestClient.mutateWith(csrf()).put()
            .uri("/api/organization/$organizationId/form/$id/update")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.type").isEqualTo("https://lyra.com/errors/entity-not-found")
            .jsonPath("$.title").isEqualTo("Entity not found")
            .jsonPath("$.status").isEqualTo(404)
            .jsonPath("$.detail").isEqualTo("Form not found")
            .jsonPath("$.instance").isEqualTo("/api/organization/$organizationId/form/$id/update")
            .jsonPath("$.errorCategory").isEqualTo("NOT_FOUND")
            .jsonPath("$.timestamp").isNotEmpty
            .consumeWith { response ->
                println(response)
            }
    }
}
