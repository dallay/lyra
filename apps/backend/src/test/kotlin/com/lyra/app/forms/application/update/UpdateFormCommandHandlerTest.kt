package com.lyra.app.forms.application.update

import com.lyra.UnitTest
import com.lyra.app.forms.FormStub
import com.lyra.app.forms.domain.Form
import com.lyra.app.forms.domain.FormFinderRepository
import com.lyra.app.forms.domain.FormRepository
import com.lyra.app.forms.domain.event.FormUpdatedEvent
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class UpdateFormCommandHandlerTest {
    private lateinit var eventPublisher: EventPublisher<FormUpdatedEvent>
    private lateinit var formRepository: FormRepository
    private lateinit var formFinderRepository: FormFinderRepository
    private lateinit var formUpdater: FormUpdater
    private lateinit var updateFormCommandHandler: UpdateFormCommandHandler
    private lateinit var form: Form

    @BeforeEach
    fun setUp() {
        eventPublisher = mockk()
        formRepository = mockk()
        formFinderRepository = mockk()
        formUpdater = FormUpdater(formRepository, formFinderRepository, eventPublisher)
        updateFormCommandHandler = UpdateFormCommandHandler(formUpdater)
        form = FormStub.generateRandomForm()

        coEvery { formRepository.update(eq(form)) } returns Unit
        coEvery {
            formFinderRepository.findByFormIdAndOrganizationId(
                eq(form.id),
                eq(form.organizationId),
            )
        } returns form
        coEvery { eventPublisher.publish(any(FormUpdatedEvent::class)) } returns Unit
    }

    @Test
    fun `should update a form`() = runBlocking {
        // Given
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
            organizationId = form.organizationId.value.toString(),
        )

        // When
        updateFormCommandHandler.handle(command)

        // Then
        coVerify(exactly = 1) {
            formRepository.update(
                withArg {
                    assert(it.id.value.toString() == form.id.value.toString())
                    assert(it.name == form.name)
                    assert(it.header == form.header)
                    assert(it.description == form.description)
                    assert(it.inputPlaceholder == form.inputPlaceholder)
                    assert(it.buttonText == form.buttonText)
                    assert(it.buttonColor.hex == form.buttonColor.hex)
                    assert(it.backgroundColor.hex == form.backgroundColor.hex)
                    assert(it.textColor.hex == form.textColor.hex)
                    assert(it.buttonTextColor.hex == form.buttonTextColor.hex)
                    assert(it.organizationId.value.toString() == form.organizationId.value.toString())
                },
            )
        }

        coVerify(exactly = 1) { eventPublisher.publish(ofType<FormUpdatedEvent>()) }
    }
}
