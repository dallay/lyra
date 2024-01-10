package com.lyra.app

import com.lyra.common.domain.Service
import com.lyra.spring.boot.bus.event.EventConfiguration
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType

@SpringBootApplication
@ComponentScan(
    basePackages = ["com.lyra"],
    basePackageClasses = [
        EventConfiguration::class,
    ],
    includeFilters = [ComponentScan.Filter(type = FilterType.ANNOTATION, classes = [Service::class])],
)
class LyraApplication

private val log: Logger = LoggerFactory.getLogger(LyraApplication::class.java)

fun main(args: Array<String>) {
    val environment = runApplication<LyraApplication>(args = args).environment

    if (log.isInfoEnabled) {
        log.info(ApplicationStartupTraces.of(environment))
    }
}
