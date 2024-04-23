package com.lyra.app.forms.infrastructure.http

import com.lyra.app.forms.application.update.UpdateFormCommand
import com.lyra.app.forms.infrastructure.http.request.UpdateFormRequest
import com.lyra.common.domain.bus.Mediator
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @created 20/4/24
 */
@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class UpdateFormController(
    mediator: Mediator,
) : ApiController(mediator) {
    @Operation(summary = "Create a form with the given data")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Updated form"),
        ApiResponse(responseCode = "400", description = "Bad request error (validation error)"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @PutMapping("/forms/update/{id}")
    suspend fun update(
        @PathVariable id: String,
        @Valid @RequestBody request: UpdateFormRequest
    ): ResponseEntity<String> {
        dispatch(
            UpdateFormCommand(
                id,
                request.name,
                request.header,
                request.description,
                request.inputPlaceholder,
                request.buttonText,
                request.buttonColor,
                request.backgroundColor,
                request.textColor,
                request.buttonTextColor,
            ),
        )

        return ResponseEntity.ok("Form updated successfully")
    }
}
