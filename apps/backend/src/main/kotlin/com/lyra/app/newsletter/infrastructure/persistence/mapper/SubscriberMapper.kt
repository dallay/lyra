package com.lyra.app.newsletter.infrastructure.persistence.mapper

import com.lyra.app.newsletter.domain.FirstName
import com.lyra.app.newsletter.domain.LastName
import com.lyra.app.newsletter.domain.Name
import com.lyra.app.newsletter.domain.Subscriber
import com.lyra.app.newsletter.domain.SubscriberId
import com.lyra.app.newsletter.infrastructure.persistence.entity.SubscriberEntity
import com.lyra.common.domain.vo.email.Email

object SubscriberMapper {
    fun Subscriber.toEntity(): SubscriberEntity {
        return SubscriberEntity.create(
            id = id.value,
            email = email.value,
            firstname = name.firstName.toString(),
            lastname = name.lastName.toString(),
            status = status,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

    fun SubscriberEntity.toDomain(): Subscriber {
        return Subscriber(
            id = SubscriberId(id),
            email = Email(email),
            name = Name(
                firstName = FirstName(firstname),
                lastName = if (lastname.isNullOrBlank()) null else lastname?.let { LastName(it) },
            ),
            status = status,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}
