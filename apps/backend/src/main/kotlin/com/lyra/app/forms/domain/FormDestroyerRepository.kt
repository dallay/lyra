package com.lyra.app.forms.domain

/**
 * Interface for a repository that handles the deletion of forms.
 */
interface FormDestroyerRepository {
    /**
     * Deletes a form with the given id.
     *
     * @param formId The id of the form to be deleted.
     */
    suspend fun delete(formId: FormId)
}
