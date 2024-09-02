package com.lyra.app.newsletter.infrastructure.http.request

import com.lyra.app.newsletter.domain.Attributes
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

/**
 * Data class representing a request to subscribe to a newsletter.
 *
 * @property email The email address of the subscriber. Must be a valid email and not blank.
 * @property firstname The first name of the subscriber (optional).
 * @property lastname The last name of the subscriber (optional).
 * @property attributes Additional attributes associated with the subscriber (optional).
 */
data class SubscribeNewsletterRequest(
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email is not valid")
    val email: String,
    val firstname: String? = null,
    val lastname: String? = null,
    val attributes: Attributes? = Attributes(),
)
