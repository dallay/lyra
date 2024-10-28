package com.lyra.app.linkpreview.application

import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.query.QueryHandler
import java.net.URI
import java.net.URL
import org.slf4j.LoggerFactory

/**
 * Query handler for fetching link previews.
 *
 * @property service the service used to fetch link previews
 * @created 16/10/24
 */
@Service
class GetLinkPreviewQueryHandler(private val service: LinkPreviewService) :
    QueryHandler<GetLinkPreviewQuery, LinkPreviewResponse> {

    /**
     * Handles a query to fetch a link preview.
     *
     * @param query the query containing the URL to fetch the link preview for
     * @return the link preview response
     */
    override suspend fun handle(query: GetLinkPreviewQuery): LinkPreviewResponse {
        log.debug("Fetching link preview for url: {}", query.url)
        val uri: URI = URI(query.url)
        val url: URL = uri.toURL()
        val linkPreview = service.fetchLinkPreview(url)
        return LinkPreviewResponse.from(linkPreview)
    }

    companion object {
        private val log = LoggerFactory.getLogger(GetLinkPreviewQueryHandler::class.java)
    }
}
