package com.lyra.app.forms.application.find

import com.lyra.app.forms.domain.FormFinderRepository
import com.lyra.app.forms.domain.FormId
import com.lyra.common.domain.Service

/**
 * This is a service class that handles the finding of forms.
 * It uses a [FormFinderRepository] to find a form by its ID.
 *
 * @property finder [FormFinderRepository] The repository to use for finding forms.
 */
@Service
class FormFinder(private val finder: FormFinderRepository) {

    /**
     * This function is used to find a form by its ID.
     * It is a suspending function, meaning it can be paused and resumed at a later time.
     * This makes it suitable for use in a coroutine context, where it can be used for non-blocking IO operations.
     *
     * @param id The ID of the form to find.
     * @return The form found, or null if no form was found with the given ID.
     */
    suspend fun find(id: FormId) = finder.findById(id)
}
