package com.lyra.app.team.infrastructure.persistence.repository

import com.lyra.app.team.infrastructure.persistence.entity.TeamEntity
import com.lyra.spring.boot.repository.ReactiveSearchRepository
import java.util.*
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TeamR2dbcRepository :
    CoroutineCrudRepository<TeamEntity, UUID>,
    ReactiveSearchRepository<TeamEntity> {
    suspend fun findAllByOrganizationId(value: UUID): Flow<TeamEntity>
}
