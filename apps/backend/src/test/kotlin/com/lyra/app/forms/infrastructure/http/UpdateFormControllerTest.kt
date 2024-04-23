package com.lyra.app.forms.infrastructure.http

import com.lyra.app.forms.FormStub
import com.lyra.app.forms.application.update.UpdateFormCommand
import com.lyra.app.forms.infrastructure.http.request.UpdateFormRequest
import com.lyra.common.domain.bus.Mediator
import io.mockk.coEvery
import io.mockk.mockk
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

class UpdateFormControllerTest {
    private val form = FormStub.create()
    private val mediator = mockk<Mediator>()
    private val id = UUID.randomUUID().toString()
    private val command = UpdateFormCommand(
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
    )
    private val controller = UpdateFormController(mediator)
    private val webTestClient = WebTestClient.bindToController(controller).build()

    @BeforeEach
    fun setUp() {
        coEvery { mediator.send(eq(command)) } returns Unit
    }

    @Test
    fun `should return 200 when form is updated successfully`() {
        val request = UpdateFormRequest(
            name = form.name,
            header = form.header,
            description = form.description,
            inputPlaceholder = form.inputPlaceholder,
            buttonText = form.buttonText,
            buttonColor = form.buttonColor.hex,
            backgroundColor = form.backgroundColor.hex,
            textColor = form.textColor.hex,
            buttonTextColor = form.buttonTextColor.hex,
        )
        webTestClient.put()
            .uri("/api/forms/update/$id")
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .isEqualTo("Form updated successfully")
        coEvery { mediator.send(eq(command)) }
    }
}
