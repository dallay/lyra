package com.lyra.app.newsletter.tag.infrastructure.persistence

import com.lyra.UnitTest
import com.lyra.app.newsletter.tag.TagStub
import com.lyra.app.newsletter.tag.domain.Tag
import com.lyra.app.newsletter.tag.domain.exceptions.TagException
import com.lyra.app.newsletter.tag.infrastructure.persistence.mapper.TagMapper.toEntity
import com.lyra.app.newsletter.tag.infrastructure.persistence.mapper.TagMapper.toEntityWithSubscribers
import com.lyra.app.newsletter.tag.infrastructure.persistence.repository.TagReactiveR2dbcRepository
import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.dao.TransientDataAccessResourceException

@UnitTest
internal class TagR2dbcRepositoryTest {
    private val tagReactiveR2dbcRepository: TagReactiveR2dbcRepository = mockk()
    private val tagR2dbcRepository = TagR2dbcRepository(tagReactiveR2dbcRepository)
    private lateinit var tags: List<Tag>

    @BeforeEach
    fun setUp() {
        tags = TagStub.randomTagsList()
        val tagEntities = tags.map { it.toEntity() }
        coEvery { tagReactiveR2dbcRepository.save(any()) } returns tagEntities.first()
    }

    @Test
    fun `should create a new tag`() = runBlocking {
        val tag = tags.first()
        tagR2dbcRepository.create(tag)
        coVerify(exactly = 1) { tagReactiveR2dbcRepository.save(tag.toEntity()) }
    }

    @Test
    fun `should throw TagException when tag already exists`() = runBlocking {
        val tag = tags.first()
        coEvery { tagReactiveR2dbcRepository.save(any()) } throws DuplicateKeyException("Duplicate key")

        val exception = org.junit.jupiter.api.assertThrows<TagException> {
            tagR2dbcRepository.create(tag)
        }

        assertEquals("Error creating tag", exception.message)
        coVerify(exactly = 1) { tagReactiveR2dbcRepository.save(tag.toEntity()) }
    }

    @Test
    fun `should find all tags by organizationId`() = runBlocking {
        val organizationId = tags.first().organizationId
        val tagEntities = tags.map { it.toEntityWithSubscribers() }
        coEvery { tagReactiveR2dbcRepository.findAllTagsByOrganizationId(organizationId.value) } returns tagEntities

        val result = tagR2dbcRepository.findAllTagsByOrganizationId(organizationId)

        assertEquals(tags.size, result.size)
        assertEquals(tags.map { it.id }, result.map { it.id })
    }

    @Test
    fun `should return empty list when no tags found for organizationId`() = runBlocking {
        val organizationId = tags.first().organizationId
        coEvery { tagReactiveR2dbcRepository.findAllTagsByOrganizationId(organizationId.value) } returns emptyList()

        val result = tagR2dbcRepository.findAllTagsByOrganizationId(organizationId)

        assertEquals(0, result.size)
    }

    @Test
    fun `should update an existing tag`() = runBlocking {
        val tag = tags.first()
        coEvery { tagReactiveR2dbcRepository.save(any()) } returns tag.toEntity()

        tagR2dbcRepository.update(tag)

        coVerify(exactly = 1) { tagReactiveR2dbcRepository.save(tag.toEntity()) }
    }

    @Test
    fun `should throw TagException when updating a tag fails`() = runBlocking {
        val tag = tags.first()
        coEvery { tagReactiveR2dbcRepository.save(any()) } throws TransientDataAccessResourceException(
            "Transient error",
        )

        val exception = org.junit.jupiter.api.assertThrows<TagException> {
            tagR2dbcRepository.update(tag)
        }

        assertEquals("Error updating tag", exception.message)
        coVerify(exactly = 1) { tagReactiveR2dbcRepository.save(tag.toEntity()) }
    }

    @Test
    fun `should find tag by id`() = runBlocking {
        val tag = tags.first()
        coEvery {
            tagReactiveR2dbcRepository.findByIdWithSubscribers(
                any(),
                any(),
            )
        } returns tag.toEntityWithSubscribers()

        val result = tagR2dbcRepository.findById(tag.organizationId, tag.id)

        assertEquals(tag.id, result?.id)
        assertEquals(tag.name, result?.name)
    }

    @Test
    fun `should return null when tag not found by id`() = runBlocking {
        val tag = tags.first()
        coEvery { tagReactiveR2dbcRepository.findByIdWithSubscribers(any(), any()) } returns null

        val result = tagR2dbcRepository.findById(tag.organizationId, tag.id)

        assertEquals(null, result)
    }

    @Test
    fun `should delete a tag successfully`() = runBlocking {
        val organizationId = tags.first().organizationId
        val tagId = tags.first().id
        coEvery {
            tagReactiveR2dbcRepository.deleteByOrganizationIdAndId(
                any(),
                any(),
            )
        } returns Unit

        tagR2dbcRepository.delete(organizationId, tagId)

        coVerify(exactly = 1) {
            tagReactiveR2dbcRepository.deleteByOrganizationIdAndId(
                organizationId.value,
                tagId.value,
            )
        }
    }

    @Test
    fun `should throw TagException when deleting a tag fails`() = runBlocking {
        val organizationId = tags.first().organizationId
        val tagId = tags.first().id
        coEvery {
            tagReactiveR2dbcRepository.deleteByOrganizationIdAndId(
                any(),
                any(),
            )
        } throws EmptyResultDataAccessException(1)

        val exception = org.junit.jupiter.api.assertThrows<TagException> {
            tagR2dbcRepository.delete(organizationId, tagId)
        }

        assertEquals("Error deleting tag", exception.message)
        coVerify(exactly = 1) {
            tagReactiveR2dbcRepository.deleteByOrganizationIdAndId(
                organizationId.value,
                tagId.value,
            )
        }
    }
}
