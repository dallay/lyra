package com.lyra.app.forms.infrastructure.http

import com.lyra.ControllerIntegrationTest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.test.context.jdbc.Sql

internal class DeleteFormControllerIntegrationTest : ControllerIntegrationTest() {
    private val organizationId = "7a27728a-8ef3-4070-b615-1d5ddf9a7863"

    @Test
    @Sql(
        "/db/form/form.sql",
    )
    @Sql(
        "/db/form/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should delete form when form is found`(): Unit = runBlocking {
        val formId = "1659d4ae-402a-4172-bf8b-0a5c54255587"
        webTestClient.mutateWith(csrf()).delete()
            .uri("/api/organization/$organizationId/form/$formId")
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty
    }

    @Test
    fun `should not delete form when form is not found`(): Unit = runBlocking {
        val formId = "94be1a32-cf2e-4dfc-892d-bdd8ac7ad354"
        webTestClient.mutateWith(csrf()).delete()
            .uri("/api/organization/$organizationId/form/$formId")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.type").isEqualTo("https://lyra.com/errors/entity-not-found")
            .jsonPath("$.title").isEqualTo("Entity not found")
            .jsonPath("$.status").isEqualTo(404)
            .jsonPath("$.detail").isEqualTo("Form not found")
            .jsonPath("$.instance").isEqualTo("/api/organization/$organizationId/form/$formId")
            .jsonPath("$.errorCategory").isEqualTo("NOT_FOUND")
            .jsonPath("$.timestamp").isNumber
    }
}
