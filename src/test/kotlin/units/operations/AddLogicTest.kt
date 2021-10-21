package units.operations

import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import services.ActionStackService
import services.ValueStackService
import services.services
import utils.IncorrectArgsException
import utils.InsufficientParamsException
import kotlin.test.Test
import kotlin.test.assertFailsWith

class AddLogicTest : KoinTest {
    private val addLogic = AddLogic()
    private val stack by inject<ValueStackService>()
    private val actionStack by inject<ActionStackService>()

    @Before
    fun setUp() {
        startKoin {
            printLogger()
            modules(services)
        }
        actionStack.init()
        stack.init()
    }

    @After
    fun stopDependencies() {
        stopKoin()
    }

    @Test
    fun getSymbol() {
        val symbol = addLogic.symbol
        assertEquals(symbol, "+")
    }

    @Test
    fun `execute happy`() {
        addLogic.execute(1.0, 1.0)
        val value = stack.pop()
        assertEquals(value, 2.0)
    }

    @Test
    fun `execute unhappy`() {
        assertFailsWith(IncorrectArgsException::class) {
            addLogic.execute(1.0)
        }
    }

    @Test
    fun `prep happy`() {
        stack.push(2.4)
        stack.push(2.9)

        val ret = addLogic.prep()
        assertTrue(ret.contentEquals(doubleArrayOf(2.4, 2.9)))
    }

    @Test
    fun `prep unhappy`() {
        stack.push(2.4)
        assertFailsWith(InsufficientParamsException::class) {
            addLogic.prep()
        }
    }

    @Test
    fun `undo happy`() {
        addLogic.execute(1.0, 1.0)
        addLogic.undo()

        val value = stack.pop()
        assertEquals(value, 1.0)
    }

    @Test
    fun `undo unhappy`() {
        assertFailsWith(InsufficientParamsException::class) {
            addLogic.undo()
        }
    }
}
