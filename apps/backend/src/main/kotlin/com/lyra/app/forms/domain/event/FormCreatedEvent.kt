package com.lyra.app.forms.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 * Event that is triggered when a form is created
 */
data class FormCreatedEvent(
    val formId: String,
) : BaseDomainEvent()
