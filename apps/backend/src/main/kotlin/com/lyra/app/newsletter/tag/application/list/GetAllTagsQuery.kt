package com.lyra.app.newsletter.tag.application.list

import com.lyra.app.newsletter.tag.application.TagResponse
import com.lyra.common.domain.bus.query.Query
import com.lyra.common.domain.presentation.PageResponse

/**
 * Query to get all tags for a specific organization.
 *
 * @property organizationId The ID of the organization to get tags for.
 */
data class GetAllTagsQuery(val organizationId: String) : Query<PageResponse<TagResponse>>
