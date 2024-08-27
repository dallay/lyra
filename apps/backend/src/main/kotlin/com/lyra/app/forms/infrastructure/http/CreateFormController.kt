package com.lyra.app.forms.infrastructure.http

import com.lyra.app.AppConstants.Paths.API
import com.lyra.app.AppConstants.Paths.FORMS_ID_V1
import com.lyra.app.forms.application.CreateFormCommand
import com.lyra.app.forms.infrastructure.http.request.CreateFormRequest
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
 * This is a REST controller for creating forms.
 * It extends the [ApiController] class and uses a [Mediator] for handling commands.
 *
 * @property mediator The [Mediator] for handling commands.
 */
@RestController
@RequestMapping(value = [API], produces = ["application/vnd.api.v1+json"])
class CreateFormController(
    private val mediator: Mediator,
) : ApiController(mediator) {

    /**
     * This function handles the PUT request for creating a form.
     * It validates the request body, dispatches a [CreateFormCommand], and returns a [ResponseEntity].
     *
     * @param id The ID of the form to create.
     * @param request The [CreateFormRequest] body containing the form data.
     * @return A [ResponseEntity] indicating the result of the operation.
     */
    @Operation(summary = "Create a form with the given data")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Created"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @PutMapping(FORMS_ID_V1)
    suspend fun create(
        @PathVariable organizationId: String,
        @PathVariable formId: String,
        @Validated @RequestBody request: CreateFormRequest
    ): ResponseEntity<String> {
        log.debug("Creating form with ID: {}", sanitizeAndJoinPathVariables(organizationId, formId, request.toString()))
        dispatch(
            CreateFormCommand(
                formId,
                request.name,
                request.header,
                request.description,
                request.inputPlaceholder,
                request.buttonText,
                request.buttonColor,
                request.backgroundColor,
                request.textColor,
                request.buttonTextColor,
                organizationId,
            ),
        )
        val url = FORMS_ID_V1.replace("{organizationId}", organizationId).replace("{formId}", formId)
        return ResponseEntity.created(URI.create("$API$url")).build()
    }

    companion object {
        private val log = LoggerFactory.getLogger(CreateFormController::class.java)
    }
}
