package com.lyra.app.newsletter.tag.infrastructure.http.request

import jakarta.validation.constraints.Email

/**
 * Data class representing a request to update a tag.
 *
 * @property name The name of the tag. It is optional and can be null.
 * @property color The color of the tag. It is optional and can be null.
 * @property subscribers A list of email addresses of subscribers. Each email must be valid.
 *                       It is optional and can be null.
 *
 * @created 22/9/24
 */
data class UpdateTagRequest(
    val name: String? = null,
    val color: String? = null,
    val subscribers: Set<@Email(message = "Invalid email address") String>? = null
)
