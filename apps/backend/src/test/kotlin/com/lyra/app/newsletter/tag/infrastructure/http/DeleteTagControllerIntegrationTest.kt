package com.lyra.app.newsletter.tag.infrastructure.http

import com.lyra.ControllerIntegrationTest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.test.context.jdbc.Sql

internal class DeleteTagControllerIntegrationTest : ControllerIntegrationTest() {
    private val organizationId = "7a27728a-8ef3-4070-b615-1d5ddf9a7863"

    @Test
    @Sql("/db/subscriber/subscriber.sql", "/db/tag/tag.sql")
    @Sql("/db/subscriber/clean.sql", "/db/tag/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    fun `should delete tag when tag is found`(): Unit = runBlocking {
        val tagId = "d667bf8b-69d7-4e32-9488-8ad9865fc644"
        webTestClient.mutateWith(csrf()).delete()
            .uri("/api/organization/$organizationId/tag/$tagId")
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty
    }
}
