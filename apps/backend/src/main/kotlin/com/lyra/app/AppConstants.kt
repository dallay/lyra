package com.lyra.app

/**
 * This object contains all the constants used in the application.
 * @created 18/8/24
 */
object AppConstants {
    const val SPRING_PROFILE_DEVELOPMENT = "dev"
    const val SPRING_PROFILE_TEST = "test"
    const val SPRING_PROFILE_PRODUCTION = "prod"
    const val SPRING_PROFILE_SWAGGER = "swagger"
    const val SPRING_PROFILE_NO_LIQUIBASE = "no-liquibase"

    object Paths {
        const val API = "/api"
        const val ORGANIZATIONS = "/organization"
        const val FORMS = "$ORGANIZATIONS/{organizationId}/form"
        const val FORMS_ID_V1 = "$ORGANIZATIONS/{organizationId}/form/{formId}"
        const val FORMS_ID = "/form/{formId}"
    }
}
