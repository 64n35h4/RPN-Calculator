package units.operations

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import services.Action
import services.ActionStackService
import services.ValueStackService
import units.OPERATIONS
import utils.IncorrectArgsException
import utils.InsufficientParamsException
import kotlin.math.sqrt

class SqrtLogic : IOperation, KoinComponent {
    private val stack by inject<ValueStackService>()
    private val actionStack by inject<ActionStackService>()
    override val symbol = "sqrt"

    override fun execute(vararg operands: Double) {
        if (operands.size != 1) {
            throw IncorrectArgsException()
        }
        actionStack.push(Action(actionType = OPERATIONS.SQT.name, actionPayload = listOf(operands[0])))
        stack.push(sqrt(operands[0]))
    }

    override fun prep(): DoubleArray {
        if (stack.isEmpty())
            throw InsufficientParamsException()
        val a = stack.pop()
        return doubleArrayOf(a)
    }

    override fun undo() {
        if (actionStack.isEmpty())
            throw InsufficientParamsException()
        val lastAction = actionStack.pop()
        stack.pop()
        stack.push(lastAction.actionPayload[0])
    }
}
