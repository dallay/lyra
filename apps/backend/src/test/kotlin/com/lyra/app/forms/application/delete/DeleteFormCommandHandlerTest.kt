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
    private var eventPublisher: EventPublisher<FormDeletedEvent> = mockk()
    private val formDestroyerRepository = mockkClass(FormDestroyerRepository::class)
    private val formDestroyer: FormDestroyer = FormDestroyer(formDestroyerRepository, eventPublisher)
    private val deleteFormCommandHandler: DeleteFormCommandHandler = DeleteFormCommandHandler(formDestroyer)
    private val formId = UUID.randomUUID().toString()

    @BeforeEach
    fun setUp() {
        coEvery { formDestroyerRepository.delete(any()) } returns Unit
        coEvery { eventPublisher.publish(any(FormDeletedEvent::class)) } returns Unit
    }

    @Test
    fun `should delete a form`() = runBlocking {
        val command = DeleteFormCommand(id = formId)
        deleteFormCommandHandler.handle(command)

        coVerify(exactly = 1) { formDestroyerRepository.delete(any()) }
        coVerify(exactly = 1) { eventPublisher.publish(any(FormDeletedEvent::class)) }
    }
}
