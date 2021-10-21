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
import units.operations.AddLogic

class UndoLogicTest: KoinTest {
    private val undoLogic = UndoLogic()
    private val addLogic = AddLogic()
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
        val symbol = undoLogic.command
        assertEquals(symbol, "undo")
    }

    @Test
    fun execute() {
        stack.push(4.5)
        actionStack.push(Action("PUSH", listOf(4.5)))
        stack.push(99.4)
        actionStack.push(Action("PUSH", listOf(99.4)))
        undoLogic.execute()
        val lastValue = stack.pop()
        assertEquals(lastValue, 4.5)
    }

    @Test
    fun `execute math`() {
        addLogic.execute(1.0, 3.0)
        undoLogic.execute()
        val lastValue = stack.pop()
        assertEquals(lastValue, 3.0)
    }
}