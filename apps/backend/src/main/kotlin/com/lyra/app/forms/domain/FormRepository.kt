package com.lyra.app.forms.domain

/**
 * Repository to manage forms
 */
interface FormRepository {
    /**
     * Create a form
     * @param form Form to create
     */
    suspend fun create(form: Form)

    /**
     * Update a form
     * @param form Form to update
     */
    suspend fun update(form: Form)
}
