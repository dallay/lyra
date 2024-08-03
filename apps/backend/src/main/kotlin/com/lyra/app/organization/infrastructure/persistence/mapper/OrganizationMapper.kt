package com.lyra.app.organization.infrastructure.persistence.mapper

import com.lyra.app.organization.domain.Organization
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.app.organization.infrastructure.persistence.entity.OrganizationEntity
import com.lyra.app.users.domain.UserId

/**
 * This object provides mapping functions to convert between domain and entity objects.
 */
object OrganizationMapper {
    /**
     * Converts a [Organization] domain object to a [OrganizationEntity].
     *
     * @receiver The [Organization] domain object to convert.
     * @return The converted [OrganizationEntity].
     */
    fun Organization.toEntity(): OrganizationEntity = OrganizationEntity(
        id = id.value,
        name = name,
        userId = userId.value,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    /**
     * Converts a [OrganizationEntity] to a [Organization] domain object.
     *
     * @receiver The [OrganizationEntity] to convert.
     * @return The converted [Organization] domain object.
     */
    fun OrganizationEntity.toDomain(): Organization = Organization(
        id = OrganizationId(id),
        name = name,
        userId = UserId(userId),
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}
