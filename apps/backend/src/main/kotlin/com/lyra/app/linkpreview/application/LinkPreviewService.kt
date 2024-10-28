package com.lyra.app.linkpreview.application

import com.lyra.app.linkpreview.domain.LinkPreview
import com.lyra.app.linkpreview.domain.LinkPreviewRepository
import com.lyra.common.domain.Service
import java.net.URL
import org.slf4j.LoggerFactory

/**
 * Service class for fetching link previews.
 *
 * @property repository the repository used to fetch link previews
 */
@Service
class LinkPreviewService(private val repository: LinkPreviewRepository) {

    /**
     * Fetches the link preview for the given URL.
     *
     * @param url the URL to fetch the link preview for
     * @return the link preview containing title, description, image URL, and site URL
     */
    suspend fun fetchLinkPreview(url: URL): LinkPreview {
        log.debug("Fetching link preview for url: {}", url)
        return repository.getPreview(url)
    }

    companion object {
        private val log = LoggerFactory.getLogger(LinkPreviewService::class.java)
    }
}
