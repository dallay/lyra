package com.lyra.app.organization.infrastructure.http

import com.lyra.app.organization.application.OrganizationCommand
import com.lyra.app.organization.infrastructure.http.request.CreateOrganizationRequest
import com.lyra.common.domain.bus.Mediator
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.net.URI
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * This controller handles the creation of an organization.
 *
 * @property mediator The [Mediator] used to dispatch commands.
 */
@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class CreateOrganizationController(
    private val mediator: Mediator,
) : ApiController(mediator) {

    /**
     * This method handles the PUT request to create an organization.
     *
     * @param id The ID of the organization.
     * @param request The request body containing the details of the organization to be created.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @Operation(summary = "Create an organization with the given data")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Created"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @PutMapping("/$ENDPOINT_ORGANIZATION/{id}")
    suspend fun create(
        @PathVariable id: String,
        @Validated @RequestBody request: CreateOrganizationRequest
    ): ResponseEntity<String> {
        val safeId = sanitizePathVariable(id)
        log.debug("Creating Organization with ID: {}", safeId)
        dispatch(
            OrganizationCommand(
                safeId,
                request.name,
                request.userId,
            ),
        )
        return ResponseEntity.created(URI.create("/api/$ENDPOINT_ORGANIZATION")).build()
    }

    companion object {
        private val log = LoggerFactory.getLogger(CreateOrganizationController::class.java)
    }
}
