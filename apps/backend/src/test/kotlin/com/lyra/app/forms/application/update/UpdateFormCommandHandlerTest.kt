package com.lyra.app.forms.application.update

import com.lyra.UnitTest
import com.lyra.app.forms.FormStub
import com.lyra.app.forms.domain.FormFinderRepository
import com.lyra.app.forms.domain.FormRepository
import com.lyra.app.forms.domain.event.FormUpdatedEvent
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class UpdateFormCommandHandlerTest {
    private var eventPublisher: EventPublisher<FormUpdatedEvent> = mockk()
    private var formRepository = mockkClass(FormRepository::class)
    private val formFinderRepository = mockkClass(FormFinderRepository::class)
    private val formUpdater: FormUpdater = FormUpdater(formRepository, formFinderRepository, eventPublisher)
    private val updateFormCommandHandler: UpdateFormCommandHandler = UpdateFormCommandHandler(formUpdater)
    private val form = FormStub.create()

    @BeforeEach
    fun setUp() {
        coEvery { formRepository.update(eq(form)) } returns Unit
        coEvery { formFinderRepository.findById(eq(form.id)) } returns form
        coEvery { eventPublisher.publish(any(FormUpdatedEvent::class)) } returns Unit
    }

    @Test
    fun `should update a form`() = runBlocking {
        val command = UpdateFormCommand(
            id = form.id.value.toString(),
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
        updateFormCommandHandler.handle(command)

        coVerify(exactly = 1) { formRepository.update(any()) }
        coVerify(exactly = 1) { eventPublisher.publish(any(FormUpdatedEvent::class)) }
    }
}
