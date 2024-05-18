package com.lyra.app.forms.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 * Event that is triggered when a form is created
 */
data class FormDeletedEvent(
    val formId: String,
) : BaseDomainEvent()
