package com.lyra.app.newsletter.infrastructure.persistence.entity

import com.lyra.app.newsletter.domain.SubscriberStatus
import com.lyra.common.domain.AuditableEntity
import java.time.LocalDateTime
import java.util.*
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("subscribers")
data class SubscriberEntity(
    // id is generated by the frontend and sent to the backend as a UUID
    val id: UUID,
    var email: String,
    var firstname: String,
    var lastname: String? = null,
    @Column("status")
    var status: SubscriberStatus,
    val organizationId: UUID,
    override var createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime? = LocalDateTime.now(),
) : AuditableEntity() {

    companion object {
        fun create(
            id: UUID,
            email: String,
            firstname: String,
            lastname: String,
            status: SubscriberStatus,
            organizationId: UUID,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime?,
        ): SubscriberEntity {
            val entity = SubscriberEntity(
                id = id,
                email = email,
                firstname = firstname,
                lastname = lastname,
                status = status,
                organizationId = organizationId,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
            return entity
        }
    }
}
