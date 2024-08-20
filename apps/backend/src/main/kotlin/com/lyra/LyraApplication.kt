package com.lyra

import com.lyra.app.AppConstants
import com.lyra.common.domain.Service
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.core.env.ConfigurableEnvironment

@SpringBootApplication
@ConfigurationPropertiesScan
@ComponentScan(
    includeFilters = [
        ComponentScan.Filter(
            type = FilterType.ANNOTATION,
            classes = [Service::class],
        ),
    ],
)
class LyraApplication

private val log: Logger = LoggerFactory.getLogger(LyraApplication::class.java)

fun main(args: Array<String>) {
    val environment = runApplication<LyraApplication>(args = args).environment
    initApplication(environment)

    if (log.isInfoEnabled) {
        log.info(ApplicationStartupTraces.of(environment))
    }
}

private fun initApplication(environment: ConfigurableEnvironment) {
    val activeProfiles = environment.activeProfiles
    if (activeProfiles.contains(AppConstants.SPRING_PROFILE_DEVELOPMENT) &&
        activeProfiles.contains(AppConstants.SPRING_PROFILE_PRODUCTION)
    ) {
        log.error(
            "You have misconfigured your application! " +
                "It should not run with both the '${AppConstants.SPRING_PROFILE_DEVELOPMENT}' " +
                "and '${AppConstants.SPRING_PROFILE_PRODUCTION}' profiles at the same time.",
        )
    }
}
