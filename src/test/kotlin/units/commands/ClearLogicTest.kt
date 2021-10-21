package units.commands

import org.junit.After
import org.junit.Before
import kotlin.test.Test

import org.junit.jupiter.api.Assertions.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import services.Action
import services.ActionStackService
import services.ValueStackService

class ClearLogicTest: KoinTest {
    private val clearLogic = ClearLogic()
    private val stack by inject<ValueStackService>()
    private val actionStack by inject<ActionStackService>()

    @Before
    fun setUp() {
        startKoin {
            printLogger()
            modules(
                module {
                    single { ValueStackService() }
                    single { ActionStackService() }
                }
            )
        }
        actionStack.init()
        stack.init()
    }

    @After
    fun stopDependencies() {
        stopKoin()
    }

    @Test
    fun getCommand() {
        val symbol = clearLogic.command
        assertEquals(symbol, "clear")
    }

    @Test
    fun execute() {
        stack.push(4.5)
        actionStack.push(Action("PUSH", listOf(4.5)))
        clearLogic.execute()
        assertTrue(stack.isEmpty())
        assertTrue(actionStack.isEmpty())
    }
}