package com.lyra.app.forms.infrastructure.http

import com.lyra.UnitTest
import com.lyra.app.forms.application.delete.DeleteFormCommand
import com.lyra.common.domain.bus.Mediator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

@UnitTest
internal class DeleteFormControllerTest {
    private val mediator: Mediator = mockk()
    private lateinit var controller: DeleteFormController
    private lateinit var webTestClient: WebTestClient
    private val id = UUID.randomUUID().toString()

    @BeforeEach
    fun setup() {
        controller = DeleteFormController(mediator)
        webTestClient = WebTestClient.bindToController(controller).build()
    }

    @Test
    fun `should delete form when form is found`() {
        val command = DeleteFormCommand(id)
        coEvery { mediator.send(command) } returns Unit

        webTestClient.delete()
            .uri("/api/forms/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty()

        val commandSlot = slot<DeleteFormCommand>()
        coVerify(exactly = 1) { mediator.send(capture(commandSlot)) }
        assertEquals(command, commandSlot.captured)
    }
}
