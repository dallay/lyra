package com.lyra.app.linkpreview.domain

import java.net.URL

/**
 * Interface representing a repository for fetching link previews.
 */
interface LinkPreviewRepository {

    /**
     * Fetches the link preview for the given URL.
     *
     * @param url the URL to fetch the link preview for
     * @return the link preview containing title, description, image URL, and site URL
     */
    suspend fun getPreview(url: URL): LinkPreview
}
