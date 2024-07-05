package com.lyra.app.organization.domain

import com.lyra.app.team.member.domain.TeamMemberRole
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OrganizationTest {

    private lateinit var organization: Organization
    private lateinit var organizationId: UUID
    private lateinit var userId: UUID

    @BeforeEach
    fun setUp() {
        organizationId = UUID.randomUUID()
        userId = UUID.randomUUID()
        organization = Organization.create(organizationId, "Test Organization", userId)
    }

    @Test
    fun `create organization`() {
        assertNotNull(organization)
        assertEquals(organizationId, organization.id.value)
        assertEquals("Test Organization", organization.name)
        assertEquals(userId, organization.userId.value)
        assertNotNull(organization.createdAt)
        assertNotNull(organization.updatedAt)
    }

    @Test
    fun `update organization`() {
        organization.update("New Name")
        assertEquals("New Name", organization.name)
        assertNotNull(organization.updatedAt)
    }

    @Test
    fun `add a team to the organization`() {
        val teamId = UUID.randomUUID().toString()
        organization.addTeam(teamId, organizationId.toString(), "New Team")
        assertEquals(1, organization.teams.size)
        assertEquals("New Team", organization.teams[0].name)
    }

    @Test
    fun `add a team member to the team`() {
        val teamId = UUID.randomUUID().toString()
        organization.addTeam(teamId, organizationId.toString(), "New Team")
        val memberId = UUID.randomUUID().toString()
        organization.addTeamMember(teamId, memberId, "EDITOR")

        val team = organization.teams[0]
        assertEquals(1, team.members.size)
        assertEquals(TeamMemberRole.EDITOR, team.members[0].role)
    }

    @Test
    fun `update team member role`() {
        val teamId = UUID.randomUUID().toString()
        organization.addTeam(teamId, organizationId.toString(), "New Team")
        val memberId = UUID.randomUUID().toString()
        organization.addTeamMember(teamId, memberId, "EDITOR")
        organization.updateTeamMemberRole(teamId, memberId, "OWNER")

        val team = organization.teams[0]
        assertEquals(TeamMemberRole.OWNER, team.members[0].role)
    }

    @Test
    fun `remove team member`() {
        val teamId = UUID.randomUUID().toString()
        organization.addTeam(teamId, organizationId.toString(), "New Team")
        val memberId = UUID.randomUUID().toString()
        organization.addTeamMember(teamId, memberId, "EDITOR")
        organization.removeTeamMember(teamId, memberId)

        val team = organization.teams[0]
        assertTrue(team.members.isEmpty())
    }
}
