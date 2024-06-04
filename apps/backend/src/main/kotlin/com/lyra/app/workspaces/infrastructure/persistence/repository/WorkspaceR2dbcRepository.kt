package com.lyra.app.workspaces.infrastructure.persistence.repository

import com.lyra.app.workspaces.infrastructure.persistence.entity.WorkspaceEntity
import com.lyra.spring.boot.repository.ReactiveSearchRepository
import java.util.*
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkspaceR2dbcRepository :
    CoroutineCrudRepository<WorkspaceEntity, UUID>,
    ReactiveSearchRepository<WorkspaceEntity>
