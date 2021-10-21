package units.commands

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import services.ActionStackService
import services.ValueStackService
import units.Operator

class UndoLogic : ICommand, KoinComponent {
    private val stack by inject<ValueStackService>()
    private val actionStack by inject<ActionStackService>()
    override val command = "undo"

    override fun execute() {
        val lastAction = actionStack.getLastAction()
        val ioOperations = HashMap<String, () -> Unit>()
        ioOperations["PUSH"] = ::undoPush
        if (listOf("PUSH").contains(lastAction)) {
            ioOperations[lastAction]?.let { it() }
        } else {
            val mathOperation = Operator.getOperatorByEnumString(lastAction)
            mathOperation.undo()
        }
    }

    private fun undoPush() {
        actionStack.pop()
        stack.pop()
    }
}
