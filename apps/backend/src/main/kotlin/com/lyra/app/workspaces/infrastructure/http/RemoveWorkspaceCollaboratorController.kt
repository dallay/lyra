package com.lyra.app.workspaces.infrastructure.http

import com.lyra.app.workspaces.application.collaborator.remove.RemoveWorkspaceCollaboratorCommand
import com.lyra.common.domain.bus.Mediator
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * This controller class is responsible for handling requests to remove a workspace collaborator.
 *
 * @property mediator The mediator used to dispatch commands.
 */
@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class RemoveWorkspaceCollaboratorController(
    private val mediator: Mediator,
) : ApiController(mediator) {

    /**
     * Removes a collaborator from a workspace.
     *
     * @param workspaceId The ID of the workspace.
     * @param userId The ID of the collaborator to remove.
     */
    @Operation(summary = "Remove a workspace collaborator")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Workspace collaborator removed successfully"),
        ApiResponse(responseCode = "404", description = "Workspace collaborator not found"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @DeleteMapping("/workspace/{workspaceId}/collaborator/{userId}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun create(@PathVariable workspaceId: String, @PathVariable userId: String) {
        log.debug("Removing collaborator")
        dispatch(
            RemoveWorkspaceCollaboratorCommand(
                workspaceId,
                userId,
            ),
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(RemoveWorkspaceCollaboratorController::class.java)
    }
}
