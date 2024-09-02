package com.lyra.app.newsletter.infrastructure.persistence.mapper

import com.lyra.app.newsletter.domain.FirstName
import com.lyra.app.newsletter.domain.LastName
import com.lyra.app.newsletter.domain.Name
import com.lyra.app.newsletter.domain.Subscriber
import com.lyra.app.newsletter.domain.SubscriberId
import com.lyra.app.newsletter.infrastructure.persistence.entity.SubscriberEntity
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.vo.email.Email

/**
 * Object responsible for mapping between the Subscriber domain object and the SubscriberEntity persistence object.
 */
object SubscriberMapper {
    /**
     * Extension function to convert a Subscriber domain object to a SubscriberEntity persistence object.
     *
     * @return The SubscriberEntity object.
     */
    fun Subscriber.toEntity(): SubscriberEntity {
        return SubscriberEntity.create(
            id = id.value,
            email = email.value,
            firstname = name.firstName.toString(),
            lastname = name.lastName.toString(),
            status = status,
            attributes = attributes,
            organizationId = organizationId.value,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

    /**
     * Function to convert a SubscriberEntity persistence object to a Subscriber domain object.
     *
     * @return The Subscriber domain object.
     */
    fun SubscriberEntity.toDomain(): Subscriber {
        return Subscriber(
            id = SubscriberId(id),
            email = Email(email),
            name = Name(
                firstName = if (firstname.isNullOrBlank()) null else FirstName(firstname),
                lastName = if (lastname.isNullOrBlank()) null else lastname?.let { LastName(it) },
            ),
            status = status,
            attributes = attributes,
            organizationId = OrganizationId(organizationId),
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}
