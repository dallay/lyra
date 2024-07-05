package com.lyra.app.organization.application.find

import com.lyra.UnitTest
import com.lyra.app.organization.application.OrganizationResponse
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.app.organization.domain.exception.OrganizationNotFoundException
import com.lyra.app.organization.infrastructure.OrganizationStub
import io.mockk.coEvery
import io.mockk.mockk
import java.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class FindTeamQueryHandlerTest {

    private lateinit var organizationFinder: OrganizationFinder
    private lateinit var findOrganizationQueryHandler: FindOrganizationQueryHandler

    @BeforeEach
    fun setup() {
        organizationFinder = mockk<OrganizationFinder>()
        findOrganizationQueryHandler = FindOrganizationQueryHandler(organizationFinder)
    }

    @Test
    fun `should return workspace response when workspace is found`() = runBlocking {
        val id = UUID.randomUUID().toString()
        val organizationId = OrganizationId(id)
        val workspace = OrganizationStub.create()
        val organizationResponse = OrganizationResponse.from(workspace)
        coEvery { organizationFinder.find(organizationId) } returns workspace

        val result = findOrganizationQueryHandler.handle(FindOrganizationQuery(id))

        assertEquals(organizationResponse, result)
    }

    @Test
    fun `should throw exception when workspace is not found`() {
        val id = UUID.randomUUID().toString()
        val organizationId = OrganizationId(id)
        coEvery { organizationFinder.find(organizationId) } returns null

        assertThrows(OrganizationNotFoundException::class.java) {
            runBlocking {
                findOrganizationQueryHandler.handle(FindOrganizationQuery(id))
            }
        }
    }
}
