package com.lyra.app.forms.application

import com.lyra.app.UnitTest
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
    private val name = "Form Name"
    private val header = "Form Header"
    private val description = "Form Description"
    private val inputPlaceholder = "Form Input Placeholder"
    private val buttonText = "Form Button Text"
    private val buttonColor = "#FFFFFF"
    private val backgroundColor = "#000000"
    private val textColor = "#FFFFFF"
    private val buttonTextColor = "#000000"

    @BeforeEach
    fun setUp() {
        coEvery { formRepository.create(any()) } returns Unit
        coEvery { eventPublisher.publish(any(FormCreatedEvent::class)) } returns Unit
    }

    @Test
    fun `should create a form`() = runBlocking {
        // Given
        val command = CreateFormCommand(
            id = UUID.randomUUID().toString(),
            name = name,
            header = header,
            description = description,
            inputPlaceholder = inputPlaceholder,
            buttonText = buttonText,
            buttonColor = buttonColor,
            backgroundColor = backgroundColor,
            textColor = textColor,
            buttonTextColor = buttonTextColor,
        )
        // When
        createFormCommandHandler.handle(command)
        // Then
        coVerify(exactly = 1) { formRepository.create(any()) }
        coVerify(exactly = 1) { eventPublisher.publish(any(FormCreatedEvent::class)) }
    }
}
