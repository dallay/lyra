package com.lyra.app.organization.infrastructure.http

import com.lyra.ControllerTest
import com.lyra.UnitTest
import com.lyra.app.organization.application.update.UpdateOrganizationCommand
import com.lyra.app.organization.infrastructure.OrganizationStub
import com.lyra.app.organization.infrastructure.http.request.UpdateOrganizationRequest
import io.mockk.coEvery
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class UpdateOrganizationControllerTest : ControllerTest() {
    private val organization = OrganizationStub.create()
    private val id = UUID.randomUUID().toString()
    private val command = UpdateOrganizationCommand(
        id = id,
        name = organization.name,
    )
    private val controller = UpdateOrganizationController(mediator)
    override val webTestClient = buildWebTestClient(controller)

    @BeforeEach
    fun setUp() {
        coEvery { mediator.send(eq(command)) } returns Unit
    }

    @Test
    fun `should return 200 when organization is updated successfully`() {
        val request = UpdateOrganizationRequest(
            name = organization.name,
        )
        webTestClient.put()
            .uri("/api/organization/update/$id")
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .isEqualTo("Organization updated successfully")
        coEvery { mediator.send(eq(command)) }
    }
}
