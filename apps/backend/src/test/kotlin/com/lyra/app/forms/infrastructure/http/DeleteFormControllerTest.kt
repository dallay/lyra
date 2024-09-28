package com.lyra.app.forms.infrastructure.http

import com.lyra.ControllerTest
import com.lyra.UnitTest
import com.lyra.app.forms.application.delete.DeleteFormCommand
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.slot
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

@UnitTest
internal class DeleteFormControllerTest : ControllerTest() {
    private lateinit var controller: DeleteFormController
    override lateinit var webTestClient: WebTestClient
    private val formId = UUID.randomUUID().toString()
    private val organizationId = UUID.randomUUID().toString()

    @BeforeEach
    fun setup() {
        controller = DeleteFormController(mediator)
        webTestClient = WebTestClient.bindToController(controller).build()
    }

    @Test
    fun `should delete form when form is found`() {
        val command = DeleteFormCommand(organizationId = organizationId, formId = formId)
        coEvery { mediator.send(command) } returns Unit

        webTestClient.delete()
            .uri("/api/organization/$organizationId/form/$formId")
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty()

        val commandSlot = slot<DeleteFormCommand>()
        coVerify(exactly = 1) { mediator.send(capture(commandSlot)) }
        assertEquals(command, commandSlot.captured)
    }
}
