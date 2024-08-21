package com.lyra.app.team.member.infrastructure.persistence.entity

import com.lyra.common.domain.Generated
import java.io.Serializable
import java.util.*

/**
 * Class representing the unique identifier of a team member.
 * @property teamId The unique identifier of the team
 * @property userId The unique identifier of the user.
 */
@Generated
data class TeamMemberEntityId(val teamId: UUID, val userId: UUID) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TeamMemberEntityId) return false

        if (teamId != other.teamId) return false
        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = teamId.hashCode()
        result = 31 * result + userId.hashCode()
        return result
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
