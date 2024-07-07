package com.lyra.app.organization.infrastructure.http

import com.lyra.UnitTest
import com.lyra.app.organization.OrganizationStub
import com.lyra.app.organization.application.OrganizationResponse
import com.lyra.app.organization.application.find.FindOrganizationQuery
import com.lyra.common.domain.bus.Mediator
import io.mockk.coEvery
import io.mockk.mockk
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

@UnitTest
internal class FindOrganizationControllerTest {
    private val mediator: Mediator = mockk()
    private lateinit var controller: FindOrganizationController
    private lateinit var webTestClient: WebTestClient
    private val id = UUID.randomUUID().toString()

    @BeforeEach
    fun setup() {
        controller = FindOrganizationController(mediator)
        webTestClient = WebTestClient.bindToController(controller).build()
    }

    @Test
    fun `should return organization when organization is found`() {
        val organization = OrganizationStub.create()
        val query = FindOrganizationQuery(id)
        val response = OrganizationResponse.from(organization)
        coEvery { mediator.send(query) } returns response

        webTestClient.get()
            .uri("/api/organization/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody(OrganizationResponse::class.java)
            .isEqualTo(response)
        coEvery { mediator.send(query) }
    }
}
