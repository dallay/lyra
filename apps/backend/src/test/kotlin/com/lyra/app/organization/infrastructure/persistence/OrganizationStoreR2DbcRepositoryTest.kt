package com.lyra.app.organization.infrastructure.persistence

import com.lyra.UnitTest
import com.lyra.app.organization.domain.Organization
import com.lyra.app.organization.domain.exception.OrganizationException
import com.lyra.app.organization.infrastructure.OrganizationStub
import com.lyra.app.organization.infrastructure.persistence.mapper.OrganizationMapper.toEntity
import com.lyra.app.organization.infrastructure.persistence.repository.OrganizationR2dbcRepository
import com.lyra.app.team.domain.Team
import com.lyra.app.users.domain.UserId
import io.mockk.coEvery
import io.mockk.mockk
import java.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.TransientDataAccessResourceException

@UnitTest
internal class OrganizationStoreR2DbcRepositoryTest {
    private val organizationRepository: OrganizationR2dbcRepository = mockk()
    private val organizationStoreR2dbcRepository =
        OrganizationStoreR2DbcRepository(organizationRepository)
    private lateinit var organization: Organization
    private lateinit var team: Team

    @BeforeEach
    fun setUp() {
        organization = OrganizationStub.create()
        team = OrganizationStub.createTeam()
        coEvery { organizationRepository.save(any()) } returns organization.toEntity()
    }

    @Test
    fun `should create organization`(): Unit = runBlocking {
        organizationStoreR2dbcRepository.create(organization)
        coEvery { organizationRepository.save(any()) }
    }

    @Test
    fun `should handle duplicate organization creation gracefully`(): Unit = runBlocking {
        coEvery { organizationRepository.save(any()) } throws DuplicateKeyException("Duplicate key")
        assertThrows<OrganizationException> {
            organizationStoreR2dbcRepository.create(organization)
        }
    }

    @Test
    fun `should update organization`(): Unit = runBlocking {
        organizationStoreR2dbcRepository.update(organization)
        coEvery { organizationRepository.save(any()) }
    }
    @Test
    fun `should handle unexpected error during organization update`(): Unit = runBlocking {
        coEvery { organizationRepository.save(any()) } throws RuntimeException("Unexpected error")
        assertThrows<RuntimeException> {
            organizationStoreR2dbcRepository.update(organization)
        }
    }

    @Test
    fun `should handle error when the form does not exist`(): Unit = runBlocking {
        coEvery { organizationRepository.save(any()) } throws TransientDataAccessResourceException("Unexpected error")
        assertThrows<OrganizationException> {
            organizationStoreR2dbcRepository.update(organization)
        }
    }

    @Test
    fun `should delete organization`(): Unit = runBlocking {
        coEvery { organizationRepository.deleteById(any()) } returns Unit
        organizationStoreR2dbcRepository.delete(organization.id)
    }

    @Test
    fun `should find organization by id`(): Unit = runBlocking {
        coEvery { organizationRepository.findById(any()) } returns organization.toEntity()
        organizationStoreR2dbcRepository.findById(organization.id)
    }

    @Test
    fun `should find all organizations`(): Unit = runBlocking {
        coEvery { organizationRepository.findOrganizationsByUserId(any()) } returns listOf(organization.toEntity())
        organizationStoreR2dbcRepository.findAll(UserId(UUID.randomUUID()))
    }
}
