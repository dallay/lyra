package com.lyra.app.team.member.domain

import com.lyra.app.team.member.domain.event.TeamMemberAddedEvent
import com.lyra.app.team.member.domain.event.TeamMemberUpdatedEvent
import com.lyra.common.domain.BaseEntity
import java.time.LocalDateTime

/**
 * Represents a team member. A team member is a user who is part of a team.
 * @property id The unique identifier of the team member.
 * @property role The role of the team member in the team.
 * @property createdAt The date and time when the team member was created.
 * @property updatedAt The date and time when the team member was last updated.
 */
data class TeamMember(
    override val id: TeamMemberId,
    var role: TeamMemberRole,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime? = createdAt
) : BaseEntity<TeamMemberId>() {
    /**
     * Updates the role of the team member and records a TeamMemberUpdatedEvent.
     * @param role The new role of the team member.
     * @see TeamMemberUpdatedEvent
     * @see TeamMemberRole
     */
    fun updateRole(role: TeamMemberRole) {
        this.role = role
        record(
            TeamMemberUpdatedEvent(
                id.value.first.toString(),
                id.value.second.toString(),
                role.toString(),
            ),
        )
    }

    companion object {
        /**
         * Creates a new team member with the specified teamId, userId, and role.
         * @param teamId The unique identifier of the team.
         * @param userId The unique identifier of the user.
         * @param role The role of the user in the team.
         * @return The newly created team member.
         */
        fun create(teamId: String, userId: String, role: TeamMemberRole): TeamMember {
            val teamMemberId = TeamMemberId(teamId, userId)
            val teamMember = TeamMember(teamMemberId, role)
            teamMember.record(
                TeamMemberAddedEvent(
                    teamMemberId.value.first.toString(),
                    teamMemberId.value.second.toString(),
                    role.toString(),
                    teamMember.createdAt.toString(),
                ),
            )
            return teamMember
        }
    }
}
