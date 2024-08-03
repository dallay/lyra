package com.lyra.app.forms.application

import com.lyra.UnitTest
import com.lyra.app.forms.FormStub
import com.lyra.app.forms.domain.Form
import com.lyra.app.forms.domain.FormRepository
import com.lyra.app.forms.domain.event.FormCreatedEvent
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class CreateFormCommandHandlerTest {
    private lateinit var eventPublisher: EventPublisher<FormCreatedEvent>
    private lateinit var formRepository: FormRepository
    private lateinit var formCreator: FormCreator
    private lateinit var createFormCommandHandler: CreateFormCommandHandler
    private lateinit var form: Form

    @BeforeEach
    fun setUp() {
        eventPublisher = mockk()
        formRepository = mockk()
        formCreator = FormCreator(formRepository, eventPublisher)
        createFormCommandHandler = CreateFormCommandHandler(formCreator)
        form = FormStub.create()

        coEvery { formRepository.create(any()) } returns Unit
        coEvery { eventPublisher.publish(any(FormCreatedEvent::class)) } returns Unit
    }

    @Test
    fun `should create a form`() = runBlocking {
        // Given
        val formId = UUID.randomUUID().toString()
        val command = CreateFormCommand(
            id = formId,
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
        createFormCommandHandler.handle(command)

        // Then
        coVerify(exactly = 1) {
            formRepository.create(
                withArg {
                    assert(it.id.value.toString() == formId)
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

        coVerify(exactly = 1) { eventPublisher.publish(ofType<FormCreatedEvent>()) }
    }
}
