package units.commands

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import services.ActionStackService
import services.ValueStackService

class ClearLogic : ICommand, KoinComponent {
    private val stack by inject<ValueStackService>()
    private val actionStack by inject<ActionStackService>()
    override val command = "clear"

    override fun execute() {
        stack.clear()
        actionStack.clear()
    }
}
