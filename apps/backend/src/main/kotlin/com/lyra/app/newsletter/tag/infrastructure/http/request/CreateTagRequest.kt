package com.lyra.app.newsletter.tag.infrastructure.http.request

import jakarta.validation.constraints.NotBlank

/**
 * Data class representing a request to create a new tag.
 *
 * @property name The name of the tag. This field is required.
 * @property color The color of the tag. This field is optional.
 * @created 15/9/24
 */
data class CreateTagRequest(
    @field:NotBlank(message = "Name is required")
    val name: String,
    val color: String? = null,
)
