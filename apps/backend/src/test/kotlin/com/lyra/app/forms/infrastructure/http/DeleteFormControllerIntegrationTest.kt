package com.lyra.app.forms.infrastructure.http

import com.lyra.ControllerIntegrationTest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.test.context.jdbc.Sql

private const val ENDPOINT = "/api/forms"

internal class DeleteFormControllerIntegrationTest : ControllerIntegrationTest() {
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
    fun `should delete form when form is found`(): Unit = runBlocking {
        val id = "1659d4ae-402a-4172-bf8b-0a5c54255587"
        webTestClient.mutateWith(csrf()).delete()
            .uri("$ENDPOINT/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty
    }

    @Test
    fun `should return OK when form is not found`(): Unit = runBlocking {
        val id = "94be1a32-cf2e-4dfc-892d-bdd8ac7ad354"
        webTestClient.mutateWith(csrf()).delete()
            .uri("$ENDPOINT/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty
    }
}
