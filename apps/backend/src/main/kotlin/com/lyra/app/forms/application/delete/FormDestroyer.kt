package com.lyra.app.forms.application.delete

import com.lyra.app.forms.application.find.FormFinder
import com.lyra.app.forms.domain.FormDestroyerRepository
import com.lyra.app.forms.domain.FormId
import com.lyra.app.forms.domain.event.FormDeletedEvent
import com.lyra.app.forms.domain.exception.FormNotFoundException
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import org.slf4j.LoggerFactory

/**
 * This service class is responsible for deleting forms.
 *
 * @property destroyer The repository that handles the deletion of forms.
 * @property eventPublisher The publisher that handles the broadcasting of form deletion events.
 */
@Service
class FormDestroyer(
    private val destroyer: FormDestroyerRepository,
    private val finder: FormFinder,
    eventPublisher: EventPublisher<FormDeletedEvent>
) {
    private val eventPublisher = EventBroadcaster<FormDeletedEvent>()

    init {
        this.eventPublisher.use(eventPublisher)
    }

    /**
     * Deletes a form with the given id.
     *
     * @param organizationId The id of the organization that owns the form.
     * @param formId The id of the form to be deleted.
     */
    suspend fun delete(organizationId: OrganizationId, formId: FormId) {
        log.debug("Deleting form with ids: {}, {}", organizationId, formId)
        val form =
            finder.find(organizationId, formId) ?: throw FormNotFoundException("Form not found")
        destroyer.delete(form.id)
        eventPublisher.publish(FormDeletedEvent(formId.value.toString()))
    }

    companion object {
        private val log = LoggerFactory.getLogger(FormDestroyer::class.java)
    }
}
