package com.lyra.app.forms.application

import com.lyra.app.forms.domain.Form
import com.lyra.app.forms.domain.FormRepository
import com.lyra.app.forms.domain.event.FormCreatedEvent
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import org.slf4j.LoggerFactory

/**
 * Service class responsible for creating forms.
 *
 * @property formRepository Repository for accessing form data.
 * @property eventPublisher Publisher for broadcasting form created events.
 */
@Service
class FormCreator(
    private val formRepository: FormRepository,
    eventPublisher: EventPublisher<FormCreatedEvent>
) {
    private val eventPublisher = EventBroadcaster<FormCreatedEvent>()

    init {
        this.eventPublisher.use(eventPublisher)
    }

    /**
     * Creates a form and publishes a [FormCreatedEvent] for each domain event pulled from the form.
     *
     * @param form The form to be created.
     */
    suspend fun create(form: Form) {
        log.debug("Creating form with name: ${form.name}")
        formRepository.create(form)
        val domainEvents = form.pullDomainEvents()
        log.debug("Pulling {} events from form", domainEvents.size)
        domainEvents.forEach {
            eventPublisher.publish(it as FormCreatedEvent)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(FormCreator::class.java)
    }
}
