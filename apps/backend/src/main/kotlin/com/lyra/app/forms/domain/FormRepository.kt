package com.lyra.app.forms.domain

/**
 * This is an interface for a repository that handles form creation and updates.
 * It has two functions, create and update, which are both suspending functions.
 * The functions take a Form object as a parameter.
 */
interface FormRepository {
    /**
     * This function is used to create a new form.
     * It is a suspending function, meaning it can be paused and resumed at a later time.
     * This makes it suitable for use in a coroutine context, where it can be used for non-blocking IO operations.
     *
     * @param form The form to create.
     */
    suspend fun create(form: Form)

    /**
     * This function is used to update an existing form.
     * It is a suspending function, meaning it can be paused and resumed at a later time.
     * This makes it suitable for use in a coroutine context, where it can be used for non-blocking IO operations.
     *
     * @param form The form to update.
     */
    suspend fun update(form: Form)
}
