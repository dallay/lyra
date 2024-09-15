package com.lyra.app.newsletter.subscriber.application.count.bytags

import com.lyra.UnitTest
import com.lyra.app.newsletter.subscriber.domain.SubscriberStatsRepository
import com.lyra.app.organization.domain.OrganizationId
import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.asFlow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class CountByTagsQueryHandlerTest {
    private val repository = mockk<SubscriberStatsRepository>()
    private val counter = SubscriberCountByTags(repository)
    private val handler = CountByTagsQueryHandler(counter)
    private val tags = listOf("tag1" to 10L, "tag2" to 5L, "tag3" to 98L).asFlow()
    private val orgId = "27172d5a-b88e-451c-9787-312706f4570d"
    private val organizationId = OrganizationId(orgId)

    @BeforeEach
    fun setUp() {
        coEvery { repository.countByTag(organizationId) } returns tags
    }

    @Test
    fun `should count subscribers by tags`(): Unit = runBlocking {
        // Given
        val query = CountByTagsQuery(orgId)

        // When
        val result = handler.handle(query)

        // Then
        coVerify(exactly = 1) { repository.countByTag(organizationId) }
        assertEquals(3, result.data.size)
        assertEquals(10L, result.data.first { it.tag == "tag1" }.count)
        assertEquals(5L, result.data.first { it.tag == "tag2" }.count)
        assertEquals(98L, result.data.first { it.tag == "tag3" }.count)
    }
}
