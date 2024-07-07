package com.lyra.app.organization.infrastructure.persistence

import com.lyra.UnitTest
import com.lyra.app.organization.domain.Organization
import com.lyra.app.organization.domain.exception.OrganizationException
import com.lyra.app.organization.infrastructure.OrganizationStub
import com.lyra.app.organization.infrastructure.persistence.mapper.OrganizationMapper.toEntity
import com.lyra.app.organization.infrastructure.persistence.repository.OrganizationR2dbcRepository
import com.lyra.app.users.domain.UserId
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.TransientDataAccessResourceException
import java.util.*

@UnitTest
internal class OrganizationStoreR2DbcRepositoryTest {
    private val organizationRepository: OrganizationR2dbcRepository = mockk()
  private val organizationStoreR2dbcRepository =
    OrganizationStoreR2DbcRepository(organizationRepository)
    private lateinit var organization: Organization

    @BeforeEach
    fun setUp() {
        organization = OrganizationStub.create()
    }

    @Test
    fun `should create organization`() = runBlocking {
      // Given
      coEvery { organizationRepository.save(any()) } returns organization.toEntity()

      // When
        organizationStoreR2dbcRepository.create(organization)

      // Then
      coVerify(exactly = 1) { organizationRepository.save(organization.toEntity()) }
    }

    @Test
    fun `should handle duplicate organization creation gracefully`(): Unit = runBlocking {
      // Given
        coEvery { organizationRepository.save(any()) } throws DuplicateKeyException("Duplicate key")

      // When / Then
        assertThrows<OrganizationException> {
            organizationStoreR2dbcRepository.create(organization)
        }
    }

    @Test
    fun `should update organization`() = runBlocking {
      // Given
      coEvery { organizationRepository.save(any()) } returns organization.toEntity()

      // When
        organizationStoreR2dbcRepository.update(organization)

      // Then
      coVerify(exactly = 1) { organizationRepository.save(organization.toEntity()) }
    }

    @Test
    fun `should handle unexpected error during organization update`(): Unit = runBlocking {
      // Given
        coEvery { organizationRepository.save(any()) } throws RuntimeException("Unexpected error")

      // When / Then
        assertThrows<RuntimeException> {
            organizationStoreR2dbcRepository.update(organization)
        }
    }

    @Test
    fun `should handle error when the form does not exist`(): Unit = runBlocking {
      // Given
        coEvery { organizationRepository.save(any()) } throws TransientDataAccessResourceException("Unexpected error")

      // When / Then
        assertThrows<OrganizationException> {
            organizationStoreR2dbcRepository.update(organization)
        }
    }

    @Test
    fun `should delete organization`() = runBlocking {
      // Given
        coEvery { organizationRepository.deleteById(any()) } returns Unit

      // When
        organizationStoreR2dbcRepository.delete(organization.id)

      // Then
      coVerify(exactly = 1) { organizationRepository.deleteById(organization.id.value) }
    }

    @Test
    fun `should find organization by id`() = runBlocking {
      // Given
        coEvery { organizationRepository.findById(any()) } returns organization.toEntity()

      // When
      val result = organizationStoreR2dbcRepository.findById(organization.id)

      // Then
      coVerify(exactly = 1) { organizationRepository.findById(organization.id.value) }
      assertEquals(organization, result)
    }

    @Test
    fun `should find all organizations`() = runBlocking {
      // Given
      val userId = UserId(UUID.randomUUID())
        coEvery { organizationRepository.findOrganizationsByUserId(any()) } returns listOf(organization.toEntity())

      // When
      val result = organizationStoreR2dbcRepository.findAll(userId)

      // Then
      coVerify(exactly = 1) { organizationRepository.findOrganizationsByUserId(userId.value) }
      assertTrue(result.isNotEmpty())
      assertEquals(organization, result.first())
    }
}
