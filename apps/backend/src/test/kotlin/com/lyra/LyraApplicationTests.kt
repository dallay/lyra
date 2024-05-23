package com.lyra

import com.lyra.app.config.TestDatabaseConfiguration
import com.lyra.spring.boot.bus.event.EventConfiguration
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.with

@SpringBootTest
class LyraApplicationTests {
    @Suppress("UnusedPrivateProperty")
    @Autowired
    private lateinit var eventConfiguration: EventConfiguration

    @Suppress("EmptyFunctionBlock")
    @Test
    fun contextLoads() {
    }
}

fun main(args: Array<String>) {
    fromApplication<LyraApplication>().with(TestDatabaseConfiguration::class).run(*args)
}
