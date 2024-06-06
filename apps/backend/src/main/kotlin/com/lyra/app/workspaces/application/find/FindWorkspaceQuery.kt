package com.lyra.app.workspaces.application.find

import com.lyra.app.workspaces.application.WorkspaceResponse
import com.lyra.common.domain.bus.query.Query

/**
 * Represents a query to find a workspace by its ID.
 *
 * @property id The ID of the workspace to find.
 */
data class FindWorkspaceQuery(
    val id: String
) : Query<WorkspaceResponse> {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FindWorkspaceQuery) return false
        return true
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
