package com.lyra

import com.lyra.app.AppConstants
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.Environment

private const val SEPARATOR_LENGTH = 58

/**
 * Utility class to log application's startup information.
 * This class is inspired by the [org.springframework.boot.StartupInfoLogger] class.
 * It logs the application's startup information in a more concise way.
 * @created 8/7/23
 */
object ApplicationStartupTraces {
    private val log: Logger = LoggerFactory.getLogger(ApplicationStartupTraces::class.java)
    private val SEPARATOR = "-".repeat(SEPARATOR_LENGTH)
    private const val BREAK = "\n"
    private const val SPACER = "  "

    /**
     * Return a string of the application startup traces.
     */
    fun of(environment: Environment): String {
        Objects.requireNonNull(environment, "Environment must not be null")
        return buildTraces(environment)
    }

    fun initApplication(environment: ConfigurableEnvironment) {
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

    private fun buildTraces(environment: Environment): String {
        val trace = StringBuilder()
        trace.append(BREAK)
            .append(SEPARATOR).append(BREAK)
            .append(SPACER).append(applicationRunningTrace(environment)).append(BREAK)
            .append(SPACER).append(url("Local", "localhost", environment)).append(BREAK)
            .append(SPACER).append(externalUrl(environment)).append(BREAK)
            .append(SPACER).append(profilesTrace(environment)).append(BREAK)
            .append(SEPARATOR).append(BREAK)
            .append(SPACER).append(configServer(environment)).append(BREAK)

        return trace.toString()
    }

    private fun applicationRunningTrace(environment: Environment): String {
        val applicationName = environment.getProperty("spring.application.name")
        return if (applicationName?.isBlank() != false) {
            "Application is running!"
        } else {
            StringBuilder().append("Application '").append(applicationName).append("' is running!")
                .toString()
        }
    }

    private fun externalUrl(environment: Environment): String {
        return try {
            val hostAddress = InetAddress.getLocalHost().hostAddress
            url("External", hostAddress, environment)
        } catch (_: UnknownHostException) {
            log.warn("The host name could not be determined, using `localhost` as fallback")
            url("External", "localhost", environment)
        }
    }

    private fun url(type: String, host: String, environment: Environment): String {
        return if (notWebEnvironment(environment)) {
            ""
        } else {
            StringBuilder()
                .append(type)
                .append(": \t")
                .append(protocol(environment))
                .append("://")
                .append(host)
                .append(":")
                .append(environment.getProperty("server.port"))
                .append(contextPath(environment))
                .toString()
        }
    }

    private fun notWebEnvironment(environment: Environment): Boolean =
        environment.getProperty("server.port")?.isBlank() ?: true

    private fun protocol(environment: Environment): String {
        return if (environment.getProperty("server.ssl.key-store")?.isBlank() != false) {
            "http"
        } else {
            "https"
        }
    }

    private fun profilesTrace(environment: Environment): String {
        val profiles = environment.activeProfiles
        return if (profiles.isEmpty()) {
            "No active profile set, running with default configuration."
        } else {
            StringBuilder().append("Profile(s): \t")
                .append(profiles.joinToString(", ")).toString()
        }
    }

    private fun contextPath(environment: Environment): String {
        val contextPath = environment.getProperty("server.servlet.context-path")
        return contextPath?.takeUnless(String::isBlank) ?: "/"
    }

    private fun configServer(environment: Environment): String {
        val configServer = environment.getProperty("configserver.status")
        return if (configServer?.isBlank() != false) {
            "No config server detected."
        } else {
            StringBuilder().append("Config Server: ").append(configServer).append(BREAK).append(SEPARATOR)
                .append(BREAK).toString()
        }
    }
}
