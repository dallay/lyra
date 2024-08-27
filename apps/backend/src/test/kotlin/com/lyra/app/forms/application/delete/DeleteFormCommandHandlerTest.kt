package com.lyra.app.forms.application.delete

import com.lyra.UnitTest
import com.lyra.app.forms.FormStub
import com.lyra.app.forms.application.find.FormFinder
import com.lyra.app.forms.domain.Form
import com.lyra.app.forms.domain.FormDestroyerRepository
import com.lyra.app.forms.domain.FormFinderRepository
import com.lyra.app.forms.domain.FormId
import com.lyra.app.forms.domain.event.FormDeletedEvent
import com.lyra.app.organization.domain.OrganizationId
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
    private lateinit var formFinderRepository: FormFinderRepository
    private lateinit var formFinder: FormFinder
    private lateinit var formDestroyer: FormDestroyer
    private lateinit var deleteFormCommandHandler: DeleteFormCommandHandler
    private lateinit var organizationId: OrganizationId
    private lateinit var formId: FormId
    private lateinit var form: Form

    @BeforeEach
    fun setUp() {
        eventPublisher = mockk()
        formDestroyerRepository = mockkClass(FormDestroyerRepository::class)
        formFinderRepository = mockkClass(FormFinderRepository::class)
        formFinder = FormFinder(formFinderRepository)
        formDestroyer = FormDestroyer(formDestroyerRepository, formFinder, eventPublisher)
        deleteFormCommandHandler = DeleteFormCommandHandler(formDestroyer)

        organizationId = OrganizationId(UUID.randomUUID())
        formId = FormId(UUID.randomUUID())
        form = FormStub.create(id = formId.value.toString(), organizationId = organizationId.value.toString())

        coEvery { formFinderRepository.findByFormIdAndOrganizationId(eq(formId), eq(organizationId)) } returns form
        coEvery { formDestroyerRepository.delete(any()) } returns Unit
        coEvery { eventPublisher.publish(any(FormDeletedEvent::class)) } returns Unit
    }

    @Test
    fun `should delete a form`() = runBlocking {
        // Given
        val command = DeleteFormCommand(
            organizationId = organizationId.value.toString(),
            formId = formId.value.toString(),
        )

        // When
        deleteFormCommandHandler.handle(command)

        // Then
        coVerify(exactly = 1) {
            formDestroyerRepository.delete(
                withArg {
                    assert(it.value.toString() == formId.value.toString())
                },
            )
        }
        coVerify(exactly = 1) { eventPublisher.publish(ofType<FormDeletedEvent>()) }
    }
}
