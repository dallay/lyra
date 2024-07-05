package com.lyra.app.organization.infrastructure.http

import com.lyra.ControllerIntegrationTest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockAuthentication
import org.springframework.test.context.jdbc.Sql

internal class GetAllOrganizationControllerIntegrationTest : ControllerIntegrationTest() {

    @Test
    @Sql(
        "/db/organization/all-organizations.sql",
    )
    @Sql(
        "/db/organization/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should get all organizations`(): Unit = runBlocking {
        webTestClient
            .mutateWith(csrf())
            .mutateWith(mockAuthentication<SecurityMockServerConfigurers.JwtMutator>(jwt()))
            .get()
            .uri("/api/organization")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.data.length()").isEqualTo(2)
    }
}
