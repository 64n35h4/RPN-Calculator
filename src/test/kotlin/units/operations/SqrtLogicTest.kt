package units.operations

import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import services.ActionStackService
import services.ValueStackService
import services.services
import utils.IncorrectArgsException
import utils.InsufficientParamsException
import kotlin.test.Test
import kotlin.test.assertFailsWith

class SqrtLogicTest : KoinTest {
    private val sqrtLogic = SqrtLogic()
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
        val symbol = sqrtLogic.symbol
        assertEquals(symbol, "sqrt")
    }

    @Test
    fun `execute happy`() {
        sqrtLogic.execute(9.0)
        val value = stack.pop()
        assertEquals(value, 3.0)
    }

    @Test
    fun `execute unhappy`() {
        assertFailsWith(IncorrectArgsException::class) {
            sqrtLogic.execute()
        }
    }

    @Test
    fun `prep happy`() {
        stack.push(2.4)

        val ret = sqrtLogic.prep()
        assertTrue(ret.contentEquals(doubleArrayOf(2.4)))
    }

    @Test
    fun `undo happy`() {
        sqrtLogic.execute(9.0)
        sqrtLogic.undo()

        val value = stack.pop()
        assertEquals(value, 9.0)
    }

    @Test
    fun `prep unhappy`() {
        assertFailsWith(InsufficientParamsException::class) {
            val ret = sqrtLogic.prep()
        }
    }

    @Test
    fun `undo unhappy`() {
        assertFailsWith(InsufficientParamsException::class) {
            sqrtLogic.undo()
        }
    }
}
