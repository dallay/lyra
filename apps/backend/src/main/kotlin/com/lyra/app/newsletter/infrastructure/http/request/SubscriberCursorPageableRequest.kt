package com.lyra.app.newsletter.infrastructure.http.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

/**
 *
 * @created 12/2/24
 */
data class SubscriberCursorPageableRequest(
    @field:NotBlank(message = "Size is required")
    @field:Min(value = 0, message = "Size must be greater or equals than 0")
    val size: Int,
    val nextPageCursor: String?,
    val prevPageCursor: String?,
    val search: String?
)
