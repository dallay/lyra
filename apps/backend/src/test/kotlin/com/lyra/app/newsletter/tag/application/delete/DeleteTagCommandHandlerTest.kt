package com.lyra.app.newsletter.tag.application.delete

import com.lyra.UnitTest
import com.lyra.app.newsletter.tag.TagStub
import com.lyra.app.newsletter.tag.domain.Tag
import com.lyra.app.newsletter.tag.domain.TagId
import com.lyra.app.newsletter.tag.domain.TagRepository
import com.lyra.app.newsletter.tag.domain.event.DeleteTagEvent
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class DeleteTagCommandHandlerTest {
    private val eventPublisher: EventPublisher<DeleteTagEvent> = mockk()
    private val tagRepository: TagRepository = mockk()
    private val tagDestroyer = TagDestroyer(tagRepository, eventPublisher)
    private val deleteTagCommandHandler = DeleteTagCommandHandler(tagDestroyer)
    private lateinit var organizationId: OrganizationId
    private lateinit var tagId: TagId
    private lateinit var tag: Tag

    @BeforeEach
    fun setUp() {
        organizationId = OrganizationId(UUID.randomUUID())
        tagId = TagId(UUID.randomUUID())
        tag = TagStub.create(
            id = tagId.value.toString(),
            organizationId = organizationId.value.toString(),
        )

        coEvery { tagRepository.delete(organizationId, tagId) } returns Unit
        coEvery { eventPublisher.publish(any(DeleteTagEvent::class)) } returns Unit
    }

    @Test
    fun `should delete a tag`() = runBlocking {
        // Given
        val command = DeleteTagCommand(
            organizationId = organizationId.value.toString(),
            tagId = tagId.value.toString(),
        )

        // When
        deleteTagCommandHandler.handle(command)

        // Then
        coVerify(exactly = 1) {
            tagRepository.delete(organizationId, tagId)
        }

        coVerify(exactly = 1) {
            eventPublisher.publish(ofType(DeleteTagEvent::class))
        }
    }
}
