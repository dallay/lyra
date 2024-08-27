package com.lyra.app.forms.infrastructure.http.request

import jakarta.validation.constraints.NotBlank

data class CreateFormRequest(
    @field:NotBlank(message = "Name is required")
    val name: String,
    @field:NotBlank(message = "Header is required")
    val header: String,
    @field:NotBlank(message = "Description is required")
    val description: String,
    @field:NotBlank(message = "Input placeholder is required")
    val inputPlaceholder: String,
    @field:NotBlank(message = "Button text is required")
    val buttonText: String,
    @field:NotBlank(message = "Button color is required")
    val buttonColor: String,
    @field:NotBlank(message = "Background color is required")
    val backgroundColor: String,
    @field:NotBlank(message = "Text color is required")
    val textColor: String,
    @field:NotBlank(message = "Button text color is required")
    val buttonTextColor: String
)
