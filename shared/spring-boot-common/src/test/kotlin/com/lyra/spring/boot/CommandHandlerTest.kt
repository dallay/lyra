package com.lyra.spring.boot

import com.lyra.common.domain.bus.HandlerNotFoundException
import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.bus.command.Command
import com.lyra.common.domain.bus.command.CommandHandler
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

private var springTestCounter = 0
private var springAsyncTestCounter = 0

@SpringBootTest(classes = [LyraAutoConfiguration::class, MyCommandHandler::class])
class CommandHandlerTest {

    init {
        springTestCounter = 0
        springAsyncTestCounter = 0
    }

    @Autowired
    lateinit var mediator: Mediator

    @Test
    fun `async commandHandler should be fired`() = runBlocking {
        mediator.send(MyCommand())

        assertTrue {
            springAsyncTestCounter == 1
        }
    }

    @Test
    fun `should throw exception if given async command does not have handler bean`() {
        val exception = assertFailsWith(HandlerNotFoundException::class) {
            runBlocking {
                mediator.send(NonExistCommand())
            }
        }

        assertNotNull(exception)
        assertEquals("handler could not be found for com.lyra.spring.boot.NonExistCommand", exception.message)
    }
}

class NonExistCommand : Command
class MyCommand : Command

class MyCommandHandler : CommandHandler<MyCommand> {
    override suspend fun handle(command: MyCommand) {
        delay(500)
        springAsyncTestCounter++
    }
}
