package com.lyra.app.organization.infrastructure.http

import com.lyra.app.organization.application.update.UpdateOrganizationCommand
import com.lyra.app.organization.infrastructure.http.request.UpdateOrganizationRequest
import com.lyra.common.domain.bus.Mediator
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * This class is a REST controller for updating organizations.
 * It extends the ApiController class and uses the Mediator pattern for handling requests.
 *
 * @property mediator The mediator used for handling requests.
 */
@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class UpdateOrganizationController(
    private val mediator: Mediator,
) : ApiController(mediator) {

    /**
     * This method handles the PUT request for updating an organization.
     * It validates the request body and dispatches an [UpdateOrganizationCommand].
     *
     * @param id The ID of the organization to update.
     * @param request The request body containing the new organization data.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @Operation(summary = "Update an organization with the given data")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Updated organization"),
        ApiResponse(responseCode = "400", description = "Bad request error (validation error)"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @PutMapping("/$ENDPOINT_ORGANIZATION/update/{id}")
    suspend fun update(
        @PathVariable id: String,
        @Validated @RequestBody request: UpdateOrganizationRequest
    ): ResponseEntity<String> {
        log.debug("Updating organization with ID: {}", id)
        dispatch(
            UpdateOrganizationCommand(
                id,
                request.name,
            ),
        )
        return ResponseEntity.ok("Organization updated successfully")
    }

    companion object {
        private val log = LoggerFactory.getLogger(UpdateOrganizationController::class.java)
    }
}
