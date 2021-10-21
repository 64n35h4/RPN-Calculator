package units

import org.junit.jupiter.api.Assertions.*
import org.koin.test.KoinTest
import utils.OperationNotFound
import kotlin.test.Test
import kotlin.test.assertFailsWith

class OperatorTest: KoinTest {
    private val operatorCls = Operator

    @Test
    fun getPossibleOPS() {
        val commands = operatorCls.getPossibleOPS().toList()
        val expectedCommands = listOf("+", "-", "*", "/", "sqrt")
        assertEquals(commands, expectedCommands)
    }

    @Test
    fun fromADDCommand() {
        val cmd = operatorCls.fromSymbol("+")
        assertEquals(operatorCls.enumMapper[OPERATIONS.ADD], cmd)
    }

    @Test
    fun getOperatorByEnumString() {
        val cmd = operatorCls.getOperatorByEnumString("add")
        assertEquals(operatorCls.enumMapper[OPERATIONS.ADD], cmd)
    }

    @Test
    fun `getOperatorByEnumString Unhappy`() {
        assertFailsWith(OperationNotFound::class) {
            operatorCls.getOperatorByEnumString("foo")
        }
    }

    @Test
    fun fromUnHappySymbolCommand() {
        assertFailsWith(OperationNotFound::class) {
            operatorCls.fromSymbol("**")
        }
    }
}