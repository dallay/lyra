package com.lyra.app.forms.application.update

import com.lyra.common.domain.bus.command.Command

/**
 *
 * @created 20/4/24
 */
data class UpdateFormCommand(
    val id: String,
    val name: String,
    val header: String,
    val description: String,
    val inputPlaceholder: String,
    val buttonText: String,
    val buttonColor: String,
    val backgroundColor: String,
    val textColor: String,
    val buttonTextColor: String,
) : Command
