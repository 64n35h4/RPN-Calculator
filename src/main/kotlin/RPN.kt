import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import services.Action
import services.ActionStackService
import services.ParserService
import services.ValueStackService
import units.Command
import units.Operator
import utils.InsufficientParamsException
import utils.OperationNotFound

class RPN : KoinComponent {
    private val parser by inject<ParserService>()
    private val actionStack by inject<ActionStackService>()
    private val stack by inject<ValueStackService>()

    private val allOps = Operator.getPossibleOPS()
    private val allCmd = Command.getPossibleCOMMANDS()

    fun init() {
        stack.init()
        actionStack.init()
    }

    fun parse(userInput: String?): Iterator<String> {
        parser.init(userInput)
        return parser.parse()
    }

    // Handler for all math operations (including cases of InsufficientParams
    private fun handleMathOperation(index: Int, operand: String) {
        try {
            val operation = Operator.fromSymbol(operand)
            val values = operation.prep()
            operation.execute(*values)
        } catch (ex: InsufficientParamsException) {
            println("Operator $operand (at location: $index): ${ex.message}")
            throw ex
        } catch (ex: OperationNotFound) {
            println("Operator $operand (at location: $index): ${ex.message}")
        }
    }

    // Handler for all commands (clear/undo...)
    private fun handleCommand(command: String) {
        val cmd = Command.fromCommand(command)
        cmd.execute()
    }

    // Default handler
    private fun handleDefault(currentOperand: String) {
        val currentAsDouble = currentOperand.toDoubleOrNull() ?: return
        actionStack.push(Action(actionType = "PUSH", actionPayload = listOf(currentAsDouble)))
        stack.push(currentAsDouble)
    }

    fun handleIterator(parseItr: Iterator<String>) {
        for ((index, currentOperand) in parseItr.withIndex()) {
            if (currentOperand.isEmpty())
                continue

            if (allOps.contains(currentOperand)) {
                try {
                    handleMathOperation(index + 1, currentOperand)
                    continue
                } catch (ex: InsufficientParamsException) {
                    break
                }
            }
            if (allCmd.contains(currentOperand)) {
                handleCommand(currentOperand)
                continue
            }
            handleDefault(currentOperand)
        }
    }

    fun display() {
        stack.display()
    }
}
