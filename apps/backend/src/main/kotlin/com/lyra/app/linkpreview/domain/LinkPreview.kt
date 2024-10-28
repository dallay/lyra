package com.lyra.app.linkpreview.domain

/**
 * Data class representing a link preview.
 *
 * @property title The title of the link.
 * @property description The description of the link.
 * @property imageUrl The URL of the image associated with the link.
 * @property url The URL of the link.
 */
data class LinkPreview(
    val title: String,
    val description: String,
    val imageUrl: String?,
    val url: String
)
