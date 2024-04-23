package com.lyra.app.forms.domain

/**
 * This is an interface for a repository that finds forms.
 * It has a single function, findById, which is a suspending function that returns a Form or null.
 * The function takes a FormId as a parameter.
 */
interface FormFinderRepository {
    /**
     * This function is used to find a form by its id.
     * It is a suspending function, meaning it can be paused and resumed at a later time.
     * This makes it suitable for use in a coroutine context, where it can be used for non-blocking IO operations.
     *
     * @param id The id of the form to find.
     * @return The form if found, or null if not found.
     */
    suspend fun findById(id: FormId): Form?
}
