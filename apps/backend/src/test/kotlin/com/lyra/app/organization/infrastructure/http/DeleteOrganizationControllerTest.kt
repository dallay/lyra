package com.lyra.app.organization.infrastructure.http

import com.lyra.UnitTest
import com.lyra.app.organization.application.delete.DeleteOrganizationCommand
import com.lyra.common.domain.bus.Mediator
import io.mockk.coEvery
import io.mockk.mockk
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

@UnitTest
internal class DeleteOrganizationControllerTest {
    private val mediator: Mediator = mockk()
    private lateinit var controller: DeleteOrganizationController
    private lateinit var webTestClient: WebTestClient
    private val id = UUID.randomUUID().toString()

    @BeforeEach
    fun setup() {
        controller = DeleteOrganizationController(mediator)
        webTestClient = WebTestClient.bindToController(controller).build()
    }

    @Test
    fun `should delete workspace`() {
        val command = DeleteOrganizationCommand(id)
        coEvery { mediator.send(command) } returns Unit

        webTestClient.delete()
            .uri("/api/organization/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty()

        coEvery { mediator.send(command) }
    }
}
