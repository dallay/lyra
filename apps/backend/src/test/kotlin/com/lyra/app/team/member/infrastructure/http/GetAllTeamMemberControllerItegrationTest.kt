package com.lyra.app.team.member.infrastructure.http

import com.lyra.ControllerIntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockAuthentication
import org.springframework.test.context.jdbc.Sql

internal class GetAllTeamMemberControllerItegrationTest : ControllerIntegrationTest() {
    @Test
    @Sql(
        "/db/organization/organization.sql",
        "/db/team/team.sql",
        "/db/team/team-member.sql",
    )
    @Sql(
        "/db/team/clean.sql", "/db/organization/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should get all team members`() {
        webTestClient
            .mutateWith(csrf())
            .mutateWith(mockAuthentication<SecurityMockServerConfigurers.JwtMutator>(jwt()))
            .get()
            .uri("/api/$ENDPOINT_TEAM_MEMBER")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data[0].teamName").isEqualTo("Test: Team Name")
            .jsonPath("$.data[0].organizationName")
            .isEqualTo("Test: My First Organization")
            .jsonPath("$.data[0].role").isEqualTo("OWNER")
            .jsonPath("$.data[0].organizationId").isEqualTo("a0654720-35dc-49d0-b508-1f7df5d915f1")
            .jsonPath("$.data[0].organizationOwnerId")
            .isEqualTo("efc4b2b8-08be-4020-93d5-f795762bf5c9")
            .jsonPath("$.data[0].teamId").isEqualTo("35b75e15-a5d9-4d15-accb-9ce3a904bc3c")
            .jsonPath("$.data[0].userId").isEqualTo("efc4b2b8-08be-4020-93d5-f795762bf5c9")
    }

    @Test
    @Sql(
        "/db/organization/organization.sql",
        "/db/team/team.sql",
        "/db/team/team-member.sql",
    )
    @Sql(
        "/db/team/clean.sql", "/db/organization/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should not get all team members when not authenticated`() {
        webTestClient
            .get()
            .uri("/api/$ENDPOINT_TEAM_MEMBER")
            .exchange()
            .expectStatus().isUnauthorized
            .expectBody()
            .jsonPath("$.data").isEqualTo("User not found")
    }
}
