package com.lyra.app.organization.infrastructure.http

import com.lyra.app.organization.application.delete.DeleteOrganizationCommand
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
 * This class is a REST controller that handles HTTP requests related to organization deletion.
 * It extends the ApiController class and uses the Mediator pattern for handling commands.
 */
@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class DeleteOrganizationController(
    mediator: Mediator,
) : ApiController(mediator) {

    /**
     * This function handles the DELETE HTTP request for deleting an organization.
     * It uses the path variable 'id' to identify the organization to be deleted.
     * The function is a suspend function, meaning it is designed to be used with Kotlin coroutines.
     * It dispatches a DeleteWorkspaceCommand with the provided id.
     *
     * @param id The id of the organization to be deleted.
     * @return The result of the DeleteWorkspaceCommand dispatch.
     */
    @Operation(summary = "Delete an organization")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Workspace deleted successfully"),
        ApiResponse(responseCode = "404", description = "Workspace not found"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @DeleteMapping("/$ENDPOINT_ORGANIZATION/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun delete(
        @PathVariable id: String
    ) {
        log.debug("Deleting organization with id: $id")
        dispatch(DeleteOrganizationCommand(id))
    }

    companion object {
        private val log = LoggerFactory.getLogger(DeleteOrganizationController::class.java)
    }
}
