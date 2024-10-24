package com.lyra.app.linkpreview.application

import com.lyra.app.linkpreview.domain.LinkPreview
import com.lyra.common.domain.bus.query.Response

/**
 *
 * @created 16/10/24
 */
data class LinkPreviewResponse(
    val title: String,
    val description: String,
    val imageUrl: String?,
    val url: String
) : Response {
    companion object {
        fun from(linkPreview: LinkPreview) = LinkPreviewResponse(
            title = linkPreview.title,
            description = linkPreview.description,
            imageUrl = linkPreview.imageUrl,
            url = linkPreview.url,
        )
    }
}
