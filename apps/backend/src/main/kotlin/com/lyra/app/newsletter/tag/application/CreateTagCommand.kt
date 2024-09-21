package com.lyra.app.newsletter.tag.application

import com.lyra.common.domain.bus.command.Command

/**
 * Command for creating a new tag.
 *
 * @property id The unique identifier of the tag.
 * @property name The name of the tag.
 * @property color The color of the tag.
 * @property organizationId The identifier of the organization the tag belongs to.
 * @property subscribers The list of emails subscribed to the tag.
 * @created 15/9/24
 */
data class CreateTagCommand(
    val id: String,
    val name: String,
    val color: String,
    val organizationId: String,
    val subscribers: List<String>? = emptyList()
) : Command
