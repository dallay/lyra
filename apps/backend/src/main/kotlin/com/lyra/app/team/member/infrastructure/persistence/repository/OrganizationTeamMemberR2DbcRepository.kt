package com.lyra.app.team.member.infrastructure.persistence.repository

import com.lyra.app.team.member.infrastructure.persistence.entity.OrganizationTeamMemberEntity
import com.lyra.app.team.member.infrastructure.persistence.entity.OrganizationTeamMemberEntityId
import java.util.*
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 *
 * @created 18/8/24
 */
@Repository
interface OrganizationTeamMemberR2DbcRepository :
    CoroutineCrudRepository<OrganizationTeamMemberEntity, OrganizationTeamMemberEntityId> {
    @Query(
        """
        SELECT
            t.name AS team_name,
            o.name AS organization_name,
            tm.role AS team_member_role,
            o.id AS organization_id,
            o.user_id AS organization_owner_id,
            t.team_id AS team_id,
            tm.user_id AS user_id
        FROM team_members tm
        JOIN teams t ON tm.team_id = t.team_id
        JOIN organizations o ON t.organization_id = o.id
        WHERE tm.user_id = :userId
    """,
    )
    fun findAllOrganizationTeamMembers(@Param("userId") userId: UUID): Flow<OrganizationTeamMemberEntity>
}
