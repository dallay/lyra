package com.lyra.app.newsletter.tag.application

import com.lyra.UnitTest
import com.lyra.app.newsletter.subscriber.SubscriberStub
import com.lyra.app.newsletter.subscriber.application.search.email.GetAllSubscribersByEmailService
import com.lyra.app.newsletter.subscriber.domain.Subscriber
import com.lyra.app.newsletter.subscriber.domain.SubscriberSearchRepository
import com.lyra.app.newsletter.tag.domain.SubscriberTagRepository
import com.lyra.app.newsletter.tag.domain.Tag
import com.lyra.app.newsletter.tag.domain.TagRepository
import com.lyra.app.newsletter.tag.domain.event.SubscriberTaggedEvent
import com.lyra.app.newsletter.tag.domain.event.TagCreatedEvent
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking
import net.datafaker.Faker
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class CreateTagCommandHandlerTest {
    private val eventPublisher: EventPublisher<TagCreatedEvent> = mockk()
    private val subscriberTaggedEventPublisher: EventPublisher<SubscriberTaggedEvent> = mockk()
    private val tagRepository = mockk<TagRepository>()
    private val subscriberSearchRepository = mockk<SubscriberSearchRepository>()
    private val getAllSubscribersByEmailService =
        GetAllSubscribersByEmailService(subscriberSearchRepository)
    private val subscriberTagRepository: SubscriberTagRepository = mockk()
    private val subscriberTagCreator =
        SubscriberTagCreator(subscriberTagRepository, subscriberTaggedEventPublisher)
    private val tagCreator = TagCreator(
        tagRepository,
        getAllSubscribersByEmailService,
        subscriberTagCreator,
        eventPublisher,
    )
    private val createTagCommandHandler = CreateTagCommandHandler(tagCreator)
    private val faker = Faker()
    private val name = faker.lorem().word()
    private val allColorSupported = listOf("default", "purple", "pink", "red", "blue", "yellow")
    private val tagId = UUID.randomUUID().toString()
    private val organizationId = OrganizationId(UUID.randomUUID())
    private lateinit var color: String
    private lateinit var subscribers: List<Subscriber>
    private lateinit var emails: List<String>

    @BeforeEach
    fun setUp() {
        color = allColorSupported.random()
        subscribers = SubscriberStub.dummyRandomSubscribersList(20)
        emails = subscribers.map { it.email.value }

        coEvery { tagRepository.create(any(Tag::class)) } returns Unit
        coEvery { subscriberTagRepository.create(any()) } returns Unit
        coEvery { subscriberSearchRepository.searchAllByEmails(organizationId, emails) } returns subscribers.asFlow()
        coEvery { eventPublisher.publish(any(TagCreatedEvent::class)) } returns Unit
        coEvery { subscriberTaggedEventPublisher.publish(any(SubscriberTaggedEvent::class)) } returns Unit
    }

    @Test
    fun `should create a tag`(): Unit = runBlocking {
        // Given
        val command = CreateTagCommand(
            id = tagId,
            name = name,
            color = color,
            organizationId = organizationId.value.toString(),
            subscribers = emails,
        )

        // When
        createTagCommandHandler.handle(command)

        // Then
        coVerify(exactly = 1) {
            tagRepository.create(
                withArg {
                    assert(it.id.value.toString() == tagId)
                    assert(it.name == name)
                    assert(it.color.value == color)
                    assert(it.organizationId == organizationId)
                },
            )
        }
    }
}
