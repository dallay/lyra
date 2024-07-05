package com.lyra.app.team.member.infrastructure.persistence.repository

import com.lyra.app.team.member.infrastructure.persistence.entity.TeamMemberEntity
import com.lyra.app.team.member.infrastructure.persistence.entity.TeamMemberId
import com.lyra.spring.boot.repository.ReactiveSearchRepository
import java.util.*
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

/**
 * This interface provides methods to interact with the team member repository.
 */
@Repository
interface TeamMemberR2dbcRepository :
    CoroutineCrudRepository<TeamMemberEntity, TeamMemberId>,
    ReactiveSearchRepository<TeamMemberEntity> {
    suspend fun findByTeamIdAndUserId(teamId: UUID, userId: UUID): TeamMemberEntity
    suspend fun deleteByTeamIdAndUserId(teamId: UUID, userId: UUID)

    /**
     * Upserts a team member.
     * This is a workaround until the Spring R2DBC team provides native support for composite primary keys.
     * The issues where this topic is being discussed are:
     * [Support for Composite Keys using @Embeded and @Id](https://github.com/spring-projects/spring-data-relational/issues/574)
     * [Add support for composite Id's](https://github.com/spring-projects/spring-data-r2dbc/issues/158)
     *
     */
    @Modifying
    @Query(
        """
    INSERT INTO team_members (team_id, user_id, role, created_at, updated_at)
    VALUES (:#{#entity.teamId}, :#{#entity.userId}, :#{#entity.role}::role_type, :#{#entity.createdAt}, :#{#entity.updatedAt})
    ON CONFLICT (team_id, user_id) DO UPDATE SET role = :#{#entity.role}::role_type, updated_at = NOW()
    """,
    )
    suspend fun upsert(entity: TeamMemberEntity): Int

    /**
     * Deletes a team member.
     *
     * @param teamId The team id.
     * @param userId The user id.
     */
    @Modifying
    @Query(
        """
    DELETE FROM team_members WHERE team_id = :teamId AND user_id = :userId
    """,
    )
    suspend fun delete(teamId: UUID, userId: UUID): Int
}
