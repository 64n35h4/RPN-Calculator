import org.koin.core.context.startKoin
import services.services

fun main() {
    // using Koin for DI - bootstrap services
    startKoin {
        printLogger()
        modules(services)
    }

    // initialize the RPN
    val rpn = RPN()
    rpn.init()

    // reading lines till user input "quit" or "exit"
    while (true) {
        print("> ")
        val enteredString = readLine()
        if (listOf("quit", "exit").contains(enteredString?.lowercase()))
            break
        val parsedIterator = rpn.parse(enteredString)
        rpn.handleIterator(parsedIterator)
        rpn.display()
    }
}
