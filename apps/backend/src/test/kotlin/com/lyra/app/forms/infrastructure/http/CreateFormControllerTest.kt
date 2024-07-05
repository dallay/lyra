package com.lyra.app.forms.infrastructure.http

import com.lyra.UnitTest
import com.lyra.app.forms.FormStub
import com.lyra.app.forms.application.CreateFormCommand
import com.lyra.app.forms.infrastructure.http.request.CreateFormRequest
import com.lyra.common.domain.bus.Mediator
import io.mockk.coEvery
import io.mockk.mockk
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

@UnitTest
internal class CreateFormControllerTest {
    private val form = FormStub.create()
    private val mediator = mockk<Mediator>()
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
        workspaceId = form.organizationId.toString(),
    )
    private val controller = CreateFormController(mediator)
    private val webTestClient = WebTestClient.bindToController(controller).build()

    @BeforeEach
    fun setUp() {
        coEvery { mediator.send(eq(command)) } returns Unit
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
            workspaceId = form.organizationId.toString(),
        )
        webTestClient.put()
            .uri("/api/forms/$id")
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody().isEmpty
        coEvery { mediator.send(eq(command)) }
    }
}
