package com.lyra.app.team.member.infrastructure.persistence.entity

import com.lyra.common.domain.AuditableEntity
import java.time.LocalDateTime
import java.util.*
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table

/**
 * This data class represents a team member entity in the persistence layer.
 *
 * @property teamId The unique identifier of the team. This field is mandatory.
 * @property userId The unique identifier of the user who is a member. This field is mandatory.
 * @property role The role of the user in the team member. This field is mandatory.
 * @property createdAt The timestamp when the team member was created. This field is mandatory.
 * @property updatedAt The timestamp when the team member was last updated. This field is optional.
 *
 * @constructor Creates a team member entity.
 */
@Table("team_members")
data class TeamMemberEntity(
    val teamId: UUID,
    val userId: UUID,
    val role: String,
    override var createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime? = createdAt,
) : AuditableEntity(createdAt, updatedAt), Persistable<UUID> {
    /**
     * This method returns the unique identifier of the team member.
     *
     * @return The unique identifier of the team member.
     */
    override fun getId(): UUID = teamId

    /**
     * This method checks if the team member is new by comparing the creation and update timestamps.
     *
     * @return A boolean indicating whether the team member is new.
     */
    override fun isNew(): Boolean = createdAt == updatedAt
}
