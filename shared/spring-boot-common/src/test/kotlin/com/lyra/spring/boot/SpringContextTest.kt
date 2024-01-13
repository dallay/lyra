package com.lyra.spring.boot

import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.bus.MediatorImpl
import kotlin.test.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [LyraAutoConfiguration::class])
class SpringContextTest {

    @Autowired
    lateinit var mediator: Mediator

    @Test
    fun contextLoads() {
        assertNotNull(mediator)
        assert(mediator is MediatorImpl)
    }
}
