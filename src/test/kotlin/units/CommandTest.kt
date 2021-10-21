package units

import org.junit.jupiter.api.Assertions.assertEquals
import org.koin.test.KoinTest
import utils.CommandNotFound
import kotlin.test.Test
import kotlin.test.assertFailsWith

class CommandTest : KoinTest {
    private val commandCls = Command

    @Test
    fun getPossibleCOMMANDS() {
        val commands = commandCls.getPossibleCOMMANDS().toList()
        val expectedCommands = listOf("undo", "clear")
        assertEquals(commands, expectedCommands)
    }

    @Test
    fun fromUndoCommand() {
        val cmd = commandCls.fromCommand("undo")
        assertEquals(commandCls.enumMapper[COMMANDS.UNDO], cmd)
    }

    @Test
    fun fromClearCommand() {
        val cmd = commandCls.fromCommand("clear")
        assertEquals(commandCls.enumMapper[COMMANDS.CLEAR], cmd)
    }

    @Test
    fun fromUnHappyCommand() {
        assertFailsWith(CommandNotFound::class) {
            commandCls.fromCommand("foo")
        }
    }
}
