package com.lyra.app.organization.infrastructure.persistence.repository

import com.lyra.app.organization.infrastructure.persistence.entity.OrganizationEntity
import com.lyra.spring.boot.repository.ReactiveSearchRepository
import java.util.*
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrganizationR2dbcRepository :
    CoroutineCrudRepository<OrganizationEntity, UUID>,
    ReactiveSearchRepository<OrganizationEntity> {
    @Query(
        """
        SELECT o.*
        FROM organizations o
        WHERE o.user_id = :userId
        UNION
        SELECT o.*
        FROM organizations o
        JOIN teams t ON o.id = t.organization_id
        JOIN team_members tm ON t.team_id = tm.team_id
        WHERE tm.user_id = :userId
    """,
    )
    suspend fun findOrganizationsByUserId(userId: UUID): List<OrganizationEntity>
}
