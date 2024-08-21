package com.lyra

import com.lyra.ApplicationStartupTraces.initApplication
import com.lyra.common.domain.Service
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType

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
