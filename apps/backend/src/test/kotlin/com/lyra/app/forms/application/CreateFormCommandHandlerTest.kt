package com.lyra.app.forms.application

import com.lyra.UnitTest
import com.lyra.app.forms.FormStub
import com.lyra.app.forms.domain.FormRepository
import com.lyra.app.forms.domain.event.FormCreatedEvent
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkClass
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class CreateFormCommandHandlerTest {
    private var eventPublisher: EventPublisher<FormCreatedEvent> = mockk()
    private var formRepository = mockkClass(FormRepository::class)
    private var formCreator: FormCreator = FormCreator(formRepository, eventPublisher)
    private var createFormCommandHandler: CreateFormCommandHandler = CreateFormCommandHandler(formCreator)
    private val form = FormStub.create()

    @BeforeEach
    fun setUp() {
        coEvery { formRepository.create(any()) } returns Unit
        coEvery { eventPublisher.publish(any(FormCreatedEvent::class)) } returns Unit
    }

    @Test
    fun `should create a form`() = runBlocking {
        val command = CreateFormCommand(
            id = UUID.randomUUID().toString(),
            name = form.name,
            header = form.header,
            description = form.description,
            inputPlaceholder = form.inputPlaceholder,
            buttonText = form.buttonText,
            buttonColor = form.buttonColor.hex,
            backgroundColor = form.backgroundColor.hex,
            textColor = form.textColor.hex,
            buttonTextColor = form.buttonTextColor.hex,
            workspaceId = form.organizationId.value.toString(),
        )
        createFormCommandHandler.handle(command)

        coVerify(exactly = 1) { formRepository.create(any()) }
        coVerify(exactly = 1) { eventPublisher.publish(any(FormCreatedEvent::class)) }
    }
}
