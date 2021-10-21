package units

import units.commands.ClearLogic
import units.commands.ICommand
import units.commands.UndoLogic
import utils.CommandNotFound

enum class COMMANDS {
    UNDO,
    CLEAR
}

class Command {
    companion object {
        val enumMapper = HashMap<COMMANDS, ICommand>()
        private var commandMapper = HashMap<String, COMMANDS>()

        init {
            enumMapper[COMMANDS.UNDO] = UndoLogic()
            enumMapper[COMMANDS.CLEAR] = ClearLogic()
            commandMapper = COMMANDS.values().associateBy { enumMapper[it]!!.command } as HashMap<String, COMMANDS>
        }

        // method to get all commands
        fun getPossibleCOMMANDS(): Set<String> {
            return commandMapper.keys
        }

        // method to get a command enum from a command string (i.e: 'undo' -> UNDO)
        fun fromCommand(command: String): ICommand {
            val operationEnum = commandMapper[command] ?: throw CommandNotFound()
            return enumMapper[operationEnum]!!
        }
    }
}
