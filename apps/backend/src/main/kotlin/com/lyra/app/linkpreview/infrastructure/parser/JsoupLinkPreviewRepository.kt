package com.lyra.app.linkpreview.infrastructure.parser

import com.lyra.app.linkpreview.domain.LinkPreview
import com.lyra.app.linkpreview.domain.LinkPreviewRepository
import java.net.URL
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository

private const val CONTENT = "content"

/**
 * Repository implementation for fetching link previews using Jsoup.
 *
 * @created 16/10/24
 */
@Repository
class JsoupLinkPreviewRepository : LinkPreviewRepository {

    /**
     * Fetches the link preview for the given URL.
     *
     * @param url the URL to fetch the link preview for
     * @return the link preview containing title, description, image URL, and site URL
     */
    @Cacheable("linkPreviews")
    override suspend fun getPreview(url: URL): LinkPreview {
        log.info("Fetching preview for URL: {}", url)
        val doc = Jsoup.connect(url.toString()).get()

        val title = doc.select("meta[property=og:title]").attr(CONTENT)
            .ifBlank { doc.title() }
        val description = doc.select("meta[property=og:description]").attr(CONTENT)
            .ifBlank { doc.select("meta[name=description]").attr(CONTENT) }
            .ifBlank { "No description available" }
        val imageUrl = doc.select("meta[property=og:image]").attr(CONTENT)
        val siteUrl = doc.select("meta[property=og:url]").attr(CONTENT)
            .ifBlank { doc.location().ifBlank { doc.baseUri() } }

        val linkPreview = LinkPreview(
            title = title,
            description = description,
            imageUrl = imageUrl,
            url = siteUrl,
        )

        log.info("Generated LinkPreview: {}", linkPreview)
        return linkPreview
    }

    companion object {
        private val log = LoggerFactory.getLogger(JsoupLinkPreviewRepository::class.java)
    }
}
