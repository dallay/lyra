package com.lyra.app.forms.infrastructure.http

import com.lyra.UnitTest
import com.lyra.app.forms.FormStub
import com.lyra.app.forms.application.FormResponse
import com.lyra.app.forms.application.find.FindFormQuery
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
internal class FindFormControllerTest {
    private val mediator: Mediator = mockk()
    private lateinit var controller: FindFormController
    private lateinit var webTestClient: WebTestClient
    private val formId = UUID.randomUUID().toString()
    private val organizationId = UUID.randomUUID().toString()

    @BeforeEach
    fun setup() {
        controller = FindFormController(mediator)
        webTestClient = WebTestClient.bindToController(controller).build()
    }

    @Test
    fun `should return form when form is found`() {
        val form = FormStub.create(organizationId = organizationId, id = formId)
        val query = FindFormQuery(organizationId = organizationId, formId = formId)
        val response = FormResponse.from(form)
        coEvery { mediator.send(query) } returns response

        webTestClient.get()
            .uri("/api/organization/$organizationId/form/$formId")
            .exchange()
            .expectStatus().isOk
            .expectBody(FormResponse::class.java)
            .isEqualTo(response)
        val querySlot = slot<FindFormQuery>()
        coVerify(exactly = 1) { mediator.send(capture(querySlot)) }
        assertEquals(query, querySlot.captured)
    }
}
