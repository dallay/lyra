package com.lyra.app.forms.infrastructure.http

import com.lyra.app.forms.application.delete.DeleteFormCommand
import com.lyra.common.domain.bus.Mediator
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * This class is a REST controller that handles HTTP requests related to form deletion.
 * It extends the ApiController class and uses the Mediator pattern for handling commands.
 */
@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class DeleteFormController(
    mediator: Mediator,
) : ApiController(mediator) {

    /**
     * This function handles the DELETE HTTP request for deleting a form.
     * It uses the path variable 'id' to identify the form to be deleted.
     * The function is a suspend function, meaning it is designed to be used with Kotlin coroutines.
     * It dispatches a DeleteFormCommand with the provided id.
     *
     * @param id The id of the form to be deleted.
     * @return The result of the DeleteFormCommand dispatch.
     */
    @Operation(summary = "Delete a form")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Form deleted successfully"),
    )
    @DeleteMapping("/forms/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun delete(
        @PathVariable id: String
    ) = dispatch(DeleteFormCommand(id))
}
