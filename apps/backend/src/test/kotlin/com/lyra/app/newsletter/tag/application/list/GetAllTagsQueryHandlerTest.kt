package com.lyra.app.newsletter.tag.application.list

import com.lyra.UnitTest
import com.lyra.app.newsletter.tag.TagStub
import com.lyra.app.newsletter.tag.domain.Tag
import com.lyra.app.newsletter.tag.domain.TagSearchRepository
import com.lyra.app.organization.domain.OrganizationId
import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class GetAllTagsQueryHandlerTest {
    private val repository = mockk<TagSearchRepository>()
    private val searcher = AllTagSearcher(repository)
    private val handler = GetAllTagsQueryHandler(searcher)
    private val orgId = "27172d5a-b88e-451c-9787-312706f4570d"
    private val organizationId = OrganizationId(orgId)
    private lateinit var tags: List<Tag>

    @BeforeEach
    fun setUp() {
        tags = TagStub.randomTagsList(30)
        coEvery { repository.findAllTagsByOrganizationId(organizationId) } returns tags
    }

    @Test
    fun `should find all tags by organization id`() = runBlocking {
        // Given
        val query = GetAllTagsQuery(orgId)

        // When
        val result = handler.handle(query)

        // Then
        coVerify(exactly = 1) { repository.findAllTagsByOrganizationId(organizationId) }
        result.data.forEachIndexed { index, tag ->
            val expectedTag = tags[index]
            assertEquals(expectedTag.id.value.toString(), tag.id.toString())
            assertEquals(expectedTag.name, tag.name)
            assertEquals(expectedTag.organizationId.value.toString(), tag.organizationId.toString())
            assertEquals(expectedTag.numberOfSubscribers(), tag.subscribers?.size ?: 0)
            assertEquals(expectedTag.createdAt.toString(), tag.createdAt.toString())
            assertEquals(expectedTag.updatedAt.toString(), tag.updatedAt.toString())
        }
    }
}
