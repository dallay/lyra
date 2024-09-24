package com.lyra.app.newsletter.tag.infrastructure.persistence

import com.lyra.UnitTest
import com.lyra.app.newsletter.tag.TagStub
import com.lyra.app.newsletter.tag.domain.SubscriberTag
import com.lyra.app.newsletter.tag.infrastructure.persistence.mapper.SubscriberTagMapper.toEntity
import com.lyra.app.newsletter.tag.infrastructure.persistence.repository.SubscriberTagReactiveR2dbcRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

@UnitTest
internal class SubscriberTagR2dbcRepositoryTest {
    private val subscriberTagReactiveR2dbcRepository: SubscriberTagReactiveR2dbcRepository = mockk()
    private val subscriberTagR2dbcRepository = SubscriberTagR2dbcRepository(subscriberTagReactiveR2dbcRepository)
    private val subscriberTag: SubscriberTag = TagStub.createSubscriberTag()

    @Test
    fun `create should successfully insert a new subscriber tag`(): Unit = runBlocking {
        val subscriberTagEntity = subscriberTag.toEntity()
        coEvery { subscriberTagReactiveR2dbcRepository.upsert(subscriberTagEntity) } returns 1

        subscriberTagR2dbcRepository.create(subscriberTag)

        coVerify(exactly = 1) { subscriberTagReactiveR2dbcRepository.upsert(subscriberTagEntity) }
    }

    @Test
    fun `update should successfully update an existing subscriber tag`(): Unit = runBlocking {
        val updateSubscriberTag = subscriberTag.copy()
        val subscriberTagEntity = updateSubscriberTag.toEntity()
        coEvery { subscriberTagReactiveR2dbcRepository.upsert(subscriberTagEntity) } returns 1

        subscriberTagR2dbcRepository.create(updateSubscriberTag)

        coVerify(exactly = 1) { subscriberTagReactiveR2dbcRepository.upsert(subscriberTagEntity) }
    }
}
