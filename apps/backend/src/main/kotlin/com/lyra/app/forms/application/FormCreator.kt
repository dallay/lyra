package com.lyra.app.forms.application

import com.lyra.app.forms.domain.Form
import com.lyra.app.forms.domain.FormRepository
import com.lyra.app.forms.domain.event.FormCreatedEvent
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import org.slf4j.LoggerFactory

/**
 *
 * @created 20/4/24
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

    suspend fun create(form: Form) {
        log.info("Creating form with name: ${form.name}")
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
