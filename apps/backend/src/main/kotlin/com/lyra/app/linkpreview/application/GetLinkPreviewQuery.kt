package com.lyra.app.linkpreview.application

import com.lyra.common.domain.bus.query.Query

/**
 * Query class for fetching a link preview.
 *
 * @property url the URL to fetch the link preview for
 */
data class GetLinkPreviewQuery(
    val url: String
) : Query<LinkPreviewResponse>
