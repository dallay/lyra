package com.lyra.app.notifications.domain

import com.lyra.common.domain.AggregateRoot
import com.lyra.common.domain.Generated
import com.lyra.common.domain.email.Email

/**
 *
 * @created 9/1/24
 */
abstract class EmailMessage(
    override val id: EmailMessageId,
    val from: Email,
    val to: Email,
    val subject: String,
    val body: String
) : AggregateRoot<EmailMessageId>() {
    @Generated
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EmailMessage) return false
        if (!super.equals(other)) return false

        if (id != other.id) return false
        if (from != other.from) return false
        if (to != other.to) return false
        if (subject != other.subject) return false
        if (body != other.body) return false

        return true
    }

    @Generated
    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + from.hashCode()
        result = 31 * result + to.hashCode()
        result = 31 * result + subject.hashCode()
        result = 31 * result + body.hashCode()
        return result
    }
}
