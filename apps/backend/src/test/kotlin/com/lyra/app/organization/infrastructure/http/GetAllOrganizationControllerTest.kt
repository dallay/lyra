package com.lyra.app.organization.infrastructure.http

import com.lyra.ControllerTest
import com.lyra.app.organization.application.OrganizationResponses
import com.lyra.app.organization.application.find.all.AllOrganizationQuery
import com.lyra.app.organization.domain.Organization
import com.lyra.app.organization.infrastructure.OrganizationStub
import io.mockk.coEvery
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockAuthentication
import org.springframework.test.web.reactive.server.WebTestClient

internal class GetAllOrganizationControllerTest : ControllerTest() {
    private val controller: GetAllOrganizationController = GetAllOrganizationController(mediator)
    override val webTestClient: WebTestClient = buildWebTestClient(controller)

    private val organizations: List<Organization> =
        OrganizationStub.dummyRandomOrganizations(size = 6, userId = userId)

    @BeforeEach
    fun setUp() {
        val query = AllOrganizationQuery(userId.toString())
        coEvery { mediator.send(eq(query)) } returns OrganizationResponses.from(organizations)
        mockSecurity()
    }
    @Test
    fun `should get all organizations`() {
        val token = jwtAuthenticationToken()
        webTestClient
            .mutateWith(csrf())
            .mutateWith(mockAuthentication<SecurityMockServerConfigurers.JwtMutator>(token))
            .get()
            .uri("/api/organization")
            .exchange()
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.data.length()").isEqualTo(organizations.size)
    }
}
