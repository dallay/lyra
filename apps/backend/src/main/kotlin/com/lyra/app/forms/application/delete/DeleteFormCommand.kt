package com.lyra.app.forms.application.delete

import com.lyra.common.domain.bus.command.Command

/**
 * Represents a command to delete a form.
 *
 * @property id Unique identifier for the form.
 */
data class DeleteFormCommand(
    val id: String
) : Command
