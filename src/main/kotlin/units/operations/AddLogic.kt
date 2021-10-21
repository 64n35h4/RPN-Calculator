package units.operations

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import services.Action
import services.ActionStackService
import services.ValueStackService
import units.OPERATIONS
import utils.IncorrectArgsException
import utils.InsufficientParamsException

class AddLogic : IOperation, KoinComponent {
    private val stack by inject<ValueStackService>()
    private val actionStack by inject<ActionStackService>()
    override val symbol = "+"

    override fun execute(vararg operands: Double) {
        if (operands.size != 2) {
            throw IncorrectArgsException()
        }
        val (a, b) = operands
        actionStack.push(Action(actionType = OPERATIONS.ADD.name, actionPayload = listOf(a, b)))
        stack.push(a + b)
    }

    override fun prep(): DoubleArray {
        if (stack.size() < 2)
            throw InsufficientParamsException()
        val b = stack.pop()
        val a = stack.pop()
        return doubleArrayOf(a, b)
    }

    override fun undo() {
        if (actionStack.size() < 1)
            throw InsufficientParamsException()
        val lastAction = actionStack.pop()
        stack.pop()
        stack.push(lastAction.actionPayload[0])
        stack.push(lastAction.actionPayload[1])
    }
}
