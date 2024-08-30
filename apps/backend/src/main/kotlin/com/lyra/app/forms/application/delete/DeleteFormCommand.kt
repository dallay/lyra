package com.lyra.app.forms.application.delete

import com.lyra.common.domain.bus.command.Command

/**
 * Represents a command to delete a form.
 *
 * @property organizationId Unique identifier for the organization.
 * @property formId Unique identifier for the form.
 */
data class DeleteFormCommand(
    val organizationId: String,
    val formId: String
) : Command
