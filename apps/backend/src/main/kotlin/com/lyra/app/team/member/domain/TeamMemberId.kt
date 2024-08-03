package com.lyra.app.team.member.domain

import com.lyra.common.domain.BaseId
import com.lyra.common.domain.Generated
import java.util.*

/**
 * The id of a team member.
 * @param teamId The team id.
 * @param userId The user id.
 */
data class TeamMemberId(private val teamId: UUID, private val userId: UUID) :
    BaseId<Pair<UUID, UUID>>(teamId to userId) {
    /**
     * Constructor to create a team member id.
     * @param teamId The team id.
     * @param userId The user id.
     */
    constructor(teamId: String, userId: String) : this(
        UUID.fromString(teamId),
        UUID.fromString(userId),
    )

    @Generated
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TeamMemberId) return false
        return teamId == other.teamId && userId == other.userId
    }

    @Generated
    override fun hashCode(): Int = 31 * teamId.hashCode() + userId.hashCode()

    @Generated
    override fun toString(): String = "TeamMemberId(teamId=$teamId, userId=$userId)"

    companion object {
        /**
         * Creates a new team member id with the specified teamId and userId.
         * @param teamId The team id.
         * @param userId The user id.
         * @return The newly created team member id.
         */
        fun create(teamId: UUID, userId: UUID) = TeamMemberId(teamId, userId)

        /**
         * Creates a new team member id with the specified teamId and userId.
         * @param teamId The team id.
         * @param userId The user id.
         * @return The newly created team member id.
         */
        fun create(teamId: String, userId: String) =
            TeamMemberId(UUID.fromString(teamId), UUID.fromString(userId))
    }
}
