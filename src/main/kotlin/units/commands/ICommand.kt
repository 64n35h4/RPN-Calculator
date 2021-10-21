package units.commands

interface ICommand {
    val command: String
    fun execute()
}
