package com.lyra.app.newsletter.tag.application.list

import com.lyra.app.newsletter.tag.application.TagResponse
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.query.QueryHandler
import com.lyra.common.domain.presentation.PageResponse
import org.slf4j.LoggerFactory

/**
 * Query handler for getting all tags for a specific organization.
 *
 * @property searcher The service used to search for tags.
 */
@Service
class GetAllTagsQueryHandler(
    private val searcher: AllTagSearcher
) : QueryHandler<GetAllTagsQuery, PageResponse<TagResponse>> {

    /**
     * Handles the query to get all tags for a specific organization.
     *
     * @param query The query containing the organization ID.
     * @return A page response containing the list of tags.
     */
    override suspend fun handle(query: GetAllTagsQuery): PageResponse<TagResponse> {
        log.debug("Searching all tags for organization ${query.organizationId}")
        return PageResponse(searcher.search(query.organizationId).map { TagResponse.from(it) })
    }

    companion object {
        private val log = LoggerFactory.getLogger(GetAllTagsQueryHandler::class.java)
    }
}
