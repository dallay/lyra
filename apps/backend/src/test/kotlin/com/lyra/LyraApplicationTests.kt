package com.lyra

import com.lyra.spring.boot.bus.event.EventConfiguration
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@IntegrationTest
class LyraApplicationTests {
    @Suppress("UnusedPrivateProperty")
    @Autowired
    private lateinit var eventConfiguration: EventConfiguration
    @Test
    fun should_load_context() = Unit
}

// fun main(args: Array<String>) {
//    fromApplication<LyraApplication>().with(TestDatabaseConfiguration::class).run(*args)
// }
