package com.lyra.app.forms.infrastructure.http

import com.lyra.app.AppConstants.Paths.API
import com.lyra.app.AppConstants.Paths.FORMS_ID
import com.lyra.app.forms.application.update.UpdateFormCommand
import com.lyra.app.forms.infrastructure.http.request.UpdateFormRequest
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
 * This is a REST controller for updating forms.
 * It extends the [ApiController] class and uses a [Mediator] for handling commands.
 *
 * @property mediator The [Mediator] for handling commands.
 */
@RestController
@RequestMapping(value = [API], produces = ["application/vnd.api.v1+json"])
class UpdateFormController(
    mediator: Mediator,
) : ApiController(mediator) {

    /**
     * This function handles the PUT request for updating a form.
     * It validates the request body, dispatches an [UpdateFormCommand], and returns a [ResponseEntity].
     *
     * @param organizationId The ID of the organization that owns the form.
     * @param formId The ID of the form to update.
     * @param request The [UpdateFormRequest] body containing the updated form data.
     * @return A [ResponseEntity] indicating the result of the operation.
     */
    @Operation(summary = "Update a form with the given data")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Updated form"),
        ApiResponse(responseCode = "400", description = "Bad request error (validation error)"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @PutMapping("${FORMS_ID}/update")
    suspend fun update(
        @PathVariable organizationId: String,
        @PathVariable formId: String,
        @Validated @RequestBody request: UpdateFormRequest
    ): ResponseEntity<String> {
        log.debug(
            "Updating form with data: {}",
            sanitizeAndJoinPathVariables(organizationId, formId, request.toString()),
        )
        dispatch(
            UpdateFormCommand(
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

        return ResponseEntity.ok("Form updated successfully")
    }

    companion object {
        private val log = LoggerFactory.getLogger(UpdateFormController::class.java)
    }
}
