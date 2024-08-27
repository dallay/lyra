package com.lyra.app.forms.infrastructure.persistence.repository

import com.lyra.app.forms.infrastructure.persistence.entity.FormEntity
import com.lyra.spring.boot.repository.ReactiveSearchRepository
import java.util.*
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FormR2dbcRepository :
    CoroutineCrudRepository<FormEntity, UUID>,
    ReactiveSearchRepository<FormEntity> {
    suspend fun findByIdAndOrganizationId(id: UUID, organizationId: UUID): FormEntity?
}
