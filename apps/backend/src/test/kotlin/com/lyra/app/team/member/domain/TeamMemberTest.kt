package com.lyra.app.team.member.domain

import com.lyra.UnitTest
import com.lyra.app.team.member.domain.event.TeamMemberAddedEvent
import com.lyra.app.team.member.domain.event.TeamMemberUpdatedEvent
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@UnitTest
internal class TeamMemberTest {
    private val teamId = UUID.randomUUID().toString()
    private val userId = UUID.randomUUID().toString()
    @Test
    fun `create should initialize TeamMember with given parameters`() {
        val role = TeamMemberRole.EDITOR

        val teamMember = TeamMember.create(teamId, userId, role)

        assertEquals(teamId, teamMember.id.value.first.toString())
        assertEquals(userId, teamMember.id.value.second.toString())
        assertEquals(role, teamMember.role)
    }

    @Test
    fun `updateRole should change the role of the TeamMember`() {
        val teamMember = TeamMember.create(teamId, userId, TeamMemberRole.EDITOR)
        val newRole = TeamMemberRole.OWNER

        teamMember.updateRole(newRole)

        assertEquals(newRole, teamMember.role)
    }

    @Test
    fun `updateRole should record TeamMemberUpdatedEvent with correct information`() {
        val teamMember = TeamMember.create(teamId, userId, TeamMemberRole.EDITOR)
        val newRole = TeamMemberRole.OWNER

        teamMember.updateRole(newRole)

        val lastEvent = teamMember.pullDomainEvents().last()
        assert(lastEvent is TeamMemberUpdatedEvent)
        lastEvent as TeamMemberUpdatedEvent

        assertEquals(teamId, lastEvent.teamId)
        assertEquals(userId, lastEvent.userId)
        assertEquals(newRole.toString(), lastEvent.role)
    }

    @Test
    fun `create should record TeamMemberAddedEvent with correct information`() {
        val teamMember = TeamMember.create(teamId, userId, TeamMemberRole.EDITOR)

        val lastEvent = teamMember.pullDomainEvents().last()
        assert(lastEvent is TeamMemberAddedEvent)
        lastEvent as TeamMemberAddedEvent

        assertEquals(teamId, lastEvent.teamId)
        assertEquals(userId, lastEvent.userId)
        assertEquals(TeamMemberRole.EDITOR.toString(), lastEvent.role)
        assertEquals(teamMember.createdAt.toString(), lastEvent.createdAt)
    }
}
