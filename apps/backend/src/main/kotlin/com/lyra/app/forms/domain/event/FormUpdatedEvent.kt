package com.lyra.app.forms.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 * Data class representing the event of a form being updated.
 *
 * @property id The unique identifier of the form.
 * @property name The new name of the form. Null if not updated.
 * @property header The new header of the form. Null if not updated.
 * @property description The new description of the form. Null if not updated.
 * @property inputPlaceholder The new input placeholder of the form. Null if not updated.
 * @property buttonText The new text of the button. Null if not updated.
 * @property buttonColor The new color of the button. Null if not updated.
 * @property backgroundColor The new background color of the form. Null if not updated.
 * @property textColor The new text color of the form. Null if not updated.
 * @property buttonTextColor The new text color of the button. Null if not updated.
 */
data class FormUpdatedEvent(
    val id: String,
    val name: String? = null,
    val header: String? = null,
    val description: String? = null,
    val inputPlaceholder: String? = null,
    val buttonText: String? = null,
    val buttonColor: String? = null,
    val backgroundColor: String? = null,
    val textColor: String? = null,
    val buttonTextColor: String? = null
) : BaseDomainEvent()
