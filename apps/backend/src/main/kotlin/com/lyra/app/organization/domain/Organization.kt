package com.lyra.app.organization.domain

import com.lyra.app.organization.domain.event.OrganizationCreatedEvent
import com.lyra.app.organization.domain.event.OrganizationUpdatedEvent
import com.lyra.app.team.domain.Team
import com.lyra.app.team.member.domain.TeamMemberRole
import com.lyra.app.users.domain.UserId
import com.lyra.common.domain.AggregateRoot
import java.time.LocalDateTime
import java.util.*

/**
 * Organization is a data class representing an organization.
 *
 * @property id The unique identifier of the organization.
 * @property name The name of the organization.
 * @property userId The unique identifier of the user who created the organization.
 * @property teams The list of teams in the organization.
 */
data class Organization(
    override val id: OrganizationId,
    var name: String,
    val userId: UserId,
    val teams: MutableList<Team> = mutableListOf(),
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime? = createdAt
) : AggregateRoot<OrganizationId>() {

    /**
     * Updates the name of the organization and records an [OrganizationUpdatedEvent].
     * @param name The new name of the organization.
     */
    fun update(name: String) {
        this.name = name
        this.updatedAt = LocalDateTime.now()
        record(OrganizationUpdatedEvent(id.value.toString(), name, userId.value.toString()))
    }

    /**
     * Adds a team to the organization.
     * @param teamId The unique identifier of the team.
     * @param organizationId The unique identifier of the organization to which the team belongs.
     * @param name The name of the team.
     */
    fun addTeam(teamId: String, organizationId: String, name: String) {
        val team = Team.create(teamId, organizationId, name)
        teams.add(team)
    }

    /**
     * Adds a team member to the team.
     * @param teamId The unique identifier of the team.
     * @param userId The unique identifier of the user.
     * @param role The role of the user in the team.
     */
    fun addTeamMember(teamId: String, userId: String, role: String) {
        val team = teams.find { it.id.value.toString() == teamId }
        team?.addMember(userId, TeamMemberRole.valueOf(role))
    }

    /**
     * Updates the role of a team member.
     * @param teamId The unique identifier of the team.
     * @param userId The unique identifier of the user.
     * @param role The new role of the user in the team.
     */
    fun updateTeamMemberRole(teamId: String, userId: String, role: String) {
        val team = teams.find { it.id.value.toString() == teamId }
        team?.updateMemberRole(userId, TeamMemberRole.valueOf(role))
    }

    /**
     * Removes a team member from the team.
     * @param teamId The unique identifier of the team.
     * @param userId The unique identifier of the user.
     */
    fun removeTeamMember(teamId: String, userId: String) {
        val team = teams.find { it.id.value.toString() == teamId }
        team?.removeMember(userId)
    }

    companion object {

        /**
         * Creates a new organization with the specified id, name, and userId.
         * @param id The unique identifier of the organization.
         * @param name The name of the organization.
         * @param userId The unique identifier of the user who created the organization.
         * @param createdAt The date and time when the organization was created.
         * @param updatedAt The date and time when the organization was last updated.
         * @return The newly created organization.
         */
        fun create(
            id: UUID,
            name: String,
            userId: UUID,
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime? = createdAt
        ): Organization {
            val organizationId = OrganizationId(id)
            val customerUserId = UserId(userId)
            val organization = Organization(
                organizationId,
                name,
                customerUserId,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
            organization.record(
                OrganizationCreatedEvent(
                    organizationId.value.toString(),
                    name,
                    customerUserId.value.toString(),
                ),
            )
            return organization
        }
    }
}
