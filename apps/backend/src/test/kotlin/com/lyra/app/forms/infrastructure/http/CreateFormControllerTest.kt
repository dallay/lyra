package com.lyra.app.forms.infrastructure.http

import com.lyra.ControllerTest
import com.lyra.UnitTest
import com.lyra.app.forms.FormStub
import com.lyra.app.forms.application.CreateFormCommand
import com.lyra.app.forms.infrastructure.http.request.CreateFormRequest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.slot
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

@UnitTest
internal class CreateFormControllerTest : ControllerTest() {
    private val form = FormStub.create()
    private val id = UUID.randomUUID().toString()
    private val command = CreateFormCommand(
        id = id,
        name = form.name,
        header = form.header,
        description = form.description,
        inputPlaceholder = form.inputPlaceholder,
        buttonText = form.buttonText,
        buttonColor = form.buttonColor.hex,
        backgroundColor = form.backgroundColor.hex,
        textColor = form.textColor.hex,
        buttonTextColor = form.buttonTextColor.hex,
        organizationId = form.organizationId.toString(),
    )
    private val controller = CreateFormController(mediator)
    override val webTestClient: WebTestClient = buildWebTestClient(controller)

    @BeforeEach
    override fun setUp() {
        super.setUp()
        coEvery { mediator.send(any<CreateFormCommand>()) } returns Unit
    }

    @Test
    fun `should create a new form`() {
        val request = CreateFormRequest(
            name = form.name,
            header = form.header,
            description = form.description,
            inputPlaceholder = form.inputPlaceholder,
            buttonText = form.buttonText,
            buttonColor = form.buttonColor.hex,
            backgroundColor = form.backgroundColor.hex,
            textColor = form.textColor.hex,
            buttonTextColor = form.buttonTextColor.hex,
            organizationId = form.organizationId.toString(),
        )

        webTestClient.put()
            .uri("/api/forms/$id")
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody().isEmpty

        val commandSlot = slot<CreateFormCommand>()
        coVerify(exactly = 1) { mediator.send(capture(commandSlot)) }
        assertEquals(command, commandSlot.captured)
    }
}
