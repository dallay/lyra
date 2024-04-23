package com.lyra.app.forms.application.update

import com.lyra.app.forms.domain.FormFinderRepository
import com.lyra.app.forms.domain.FormId
import com.lyra.app.forms.domain.FormRepository
import com.lyra.app.forms.domain.dto.FormDTO
import com.lyra.app.forms.domain.event.FormUpdatedEvent
import com.lyra.app.forms.domain.exception.FormNotFoundException
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import org.slf4j.LoggerFactory

/**
 * Service class responsible for updating forms.
 *
 * @property formRepository The repository for form data.
 * @property formFinderRepository The repository for finding forms.
 * @property eventPublisher The publisher for form update events.
 */
@Service
class FormUpdater(
    private val formRepository: FormRepository,
    private val formFinderRepository: FormFinderRepository,
    eventPublisher: EventPublisher<FormUpdatedEvent>
) {
    private val eventPublisher = EventBroadcaster<FormUpdatedEvent>()

    init {
        this.eventPublisher.use(eventPublisher)
    }

    /**
     * Updates a form with the given id and DTO.
     * Throws a FormNotFoundException if the form is not found.
     *
     * @param id The id of the form to update.
     * @param formDTO The DTO containing the new form data.
     */
    suspend fun update(id: FormId, formDTO: FormDTO) {
        log.info("Updating form with name: ${formDTO.name}")
        val form = formFinderRepository.findById(id) ?: throw FormNotFoundException("Form not found")
        form.update(formDTO)
        formRepository.update(form)
        val domainEvents = form.pullDomainEvents()
        log.debug("Pulling {} events from form", domainEvents.size)
        domainEvents.forEach {
            eventPublisher.publish(it as FormUpdatedEvent)
        }
    }

    companion object {
        /**
         * Logger for the FormUpdater class.
         */
        private val log = LoggerFactory.getLogger(FormUpdater::class.java)
    }
}
