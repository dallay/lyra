package com.lyra.app.team.domain

import com.lyra.UnitTest
import com.lyra.app.team.domain.event.TeamCreatedEvent
import com.lyra.app.team.member.domain.TeamMemberRole
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@UnitTest
internal class TeamTest {
    private val userId = UUID.randomUUID().toString()
    private val teamId = UUID.randomUUID().toString()
    private val userIdThatDoesNotExist = UUID.randomUUID().toString()
    private val organizationId = UUID.randomUUID().toString()

    @Test
    fun `updateName changes the name of the team`() {
        val team = Team.create(teamId, organizationId, "Initial Name")
        team.updateName("Updated Name")
        assertEquals("Updated Name", team.name)
    }

    @Test
    fun `addMember adds a new member to the team`() {
        val team = Team.create(teamId, organizationId, "Team Name")
        team.addMember(userId, TeamMemberRole.EDITOR)
        assertTrue(team.members.any { it.id.value.second.toString() == userId && it.role == TeamMemberRole.EDITOR })
    }

    @Test
    fun `addMember does not add a duplicate member`() {
        val team = Team.create(teamId, organizationId, "Team Name")
        team.addMember(userId, TeamMemberRole.EDITOR)
        team.addMember(userId, TeamMemberRole.EDITOR)
        assertEquals(1, team.members.count { it.id.value.second.toString() == userId })
    }

    @Test
    fun `updateMemberRole updates the role of an existing member`() {
        val team = Team.create(teamId, organizationId, "Team Name")
        team.addMember(userId, TeamMemberRole.EDITOR)
        team.updateMemberRole(userId, TeamMemberRole.OWNER)
        assertTrue(team.members.any { it.id.value.second.toString() == userId && it.role == TeamMemberRole.OWNER })
    }

    @Test
    fun `updateMemberRole does nothing when member does not exist`() {
        val team = Team.create(teamId, organizationId, "Team Name")
        team.updateMemberRole(userIdThatDoesNotExist, TeamMemberRole.OWNER)
        assertTrue(team.members.none { it.id.value.second.toString() == userIdThatDoesNotExist })
    }

    @Test
    fun `removeMember removes an existing member from the team`() {
        val team = Team.create(teamId, organizationId, "Team Name")
        team.addMember(userId, TeamMemberRole.EDITOR)
        team.removeMember(userId)
        assertTrue(team.members.none { it.id.value.second.toString() == userId })
    }

    @Test
    fun `removeMember does nothing when member does not exist`() {
        val team = Team.create(teamId, organizationId, "Team Name")
        team.removeMember(userIdThatDoesNotExist)
        assertEquals(0, team.members.size)
    }

    @Test
    fun `create records a TeamCreatedEvent`() {
        val team = Team.create(teamId, organizationId, "New Team")
        val events = team.pullDomainEvents()
        assertTrue(events.any { it is TeamCreatedEvent })
        assertEquals(1, events.size)
    }
}
