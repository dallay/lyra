package com.lyra.app.team.domain

import com.lyra.app.organization.domain.OrganizationId
import com.lyra.app.team.domain.event.TeamAddedEvent
import com.lyra.app.team.domain.event.TeamUpdatedEvent
import com.lyra.app.team.member.domain.TeamMember
import com.lyra.app.team.member.domain.TeamMemberRole
import com.lyra.app.team.member.domain.event.TeamMemberRemovedEvent
import com.lyra.app.users.domain.UserId
import com.lyra.common.domain.BaseEntity
import java.time.LocalDateTime

/**
 * Team is a domain class representing a team entity.
 * It extends BaseEntity with TeamId as the identifier.
 * @property id The unique identifier of the team.
 * @property organizationId The unique identifier of the organization to which the team belongs.
 * @property name The name of the team.
 * @property createdAt The date and time when the team was created.
 * @property updatedAt The date and time when the team was last updated.
 */
data class Team(
    override val id: TeamId,
    val organizationId: OrganizationId,
    var name: String,
    val members: MutableList<TeamMember> = mutableListOf(),
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime? = createdAt
) : BaseEntity<TeamId>() {
    /**
     * Updates the name of the team and records a TeamUpdatedEvent.
     * @param name The new name of the team.
     */
    fun updateName(name: String) {
        this.name = name
        record(
            TeamUpdatedEvent(
                id.value.toString(),
                organizationId.value.toString(),
                name = name,
            ),
        )
    }

    /**
     * Adds a team member to the team.
     * @param userId The unique identifier of the user.
     * @param role The role of the user in the team.
     */
    fun addMember(userId: String, role: TeamMemberRole) {
        val userID = UserId(userId)
        // Check if the user is already a member. If so, do nothing.
        if (members.any { it.id.value.second == userID.value }) return
        val teamMember = TeamMember.create(id.value.toString(), userId, role)
        members.add(teamMember)
    }

    /**
     * Updates the role of a team member.
     * @param userId The unique identifier of the user.
     * @param role The new role of the user in the team.
     */
    fun updateMemberRole(userId: String, role: TeamMemberRole) {
        val userID = UserId(userId)
        val teamMember = members.find { it.id.value.second == userID.value }
        teamMember?.updateRole(role)
    }

    /**
     * Removes a team member from the team.
     * @param userId The unique identifier of the user.
     */
    fun removeMember(userId: String) {
        val userID = UserId(userId)
        members.removeIf { it.id.value.second == userID.value }
        record(
            TeamMemberRemovedEvent(
                id.value.toString(),
                userId,
            ),
        )
    }

    companion object {
        /**
         * Creates a new team with the specified teamId, organizationId, and name.
         * @param teamId The unique identifier of the team.
         * @param organizationId The unique identifier of the organization to which the team belongs.
         * @param name The name of the team.
         * @return The newly created team.
         */
        fun create(
            teamId: String,
            organizationId: String,
            name: String
        ): Team {
            val tId = TeamId(teamId)
            val oId = OrganizationId(organizationId)
            val team = Team(tId, oId, name)
            team.record(
                TeamAddedEvent(
                    tId.value.toString(),
                    oId.value.toString(),
                    name,
                ),
            )
            return team
        }
    }
}
