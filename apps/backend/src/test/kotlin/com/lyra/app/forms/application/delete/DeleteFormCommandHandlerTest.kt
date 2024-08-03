package com.lyra.app.forms.application.delete

import com.lyra.UnitTest
import com.lyra.app.forms.domain.FormDestroyerRepository
import com.lyra.app.forms.domain.event.FormDeletedEvent
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
internal class DeleteFormCommandHandlerTest {
    private lateinit var eventPublisher: EventPublisher<FormDeletedEvent>
    private lateinit var formDestroyerRepository: FormDestroyerRepository
    private lateinit var formDestroyer: FormDestroyer
    private lateinit var deleteFormCommandHandler: DeleteFormCommandHandler
    private lateinit var formId: String

    @BeforeEach
    fun setUp() {
        eventPublisher = mockk()
        formDestroyerRepository = mockkClass(FormDestroyerRepository::class)
        formDestroyer = FormDestroyer(formDestroyerRepository, eventPublisher)
        deleteFormCommandHandler = DeleteFormCommandHandler(formDestroyer)
        formId = UUID.randomUUID().toString()

        coEvery { formDestroyerRepository.delete(any()) } returns Unit
        coEvery { eventPublisher.publish(any(FormDeletedEvent::class)) } returns Unit
    }

    @Test
    fun `should delete a form`() = runBlocking {
        // Given
        val command = DeleteFormCommand(id = formId)

        // When
        deleteFormCommandHandler.handle(command)

        // Then
        coVerify(exactly = 1) {
            formDestroyerRepository.delete(
                withArg {
                    assert(it.value.toString() == formId)
                },
            )
        }
        coVerify(exactly = 1) { eventPublisher.publish(ofType<FormDeletedEvent>()) }
    }
}
