package com.lyra.app.newsletter.infrastructure.http.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SubscribeNewsletterRequest(
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email is not valid")
    val email: String,
    @field:NotBlank(message = "Firstname is required")
    var firstname: String,
    var lastname: String? = null,
    @field:NotBlank(message = "Workspace ID is required")
    val workspaceId: String
)
