package services

class ParserService : IParserService {
    lateinit var userInput: String

    override fun init(raw_input: String?) {
        userInput = raw_input.orEmpty()
    }

    // Creating an iterator in case of large input
    override fun parse(): Iterator<String> {
        return userInput.splitToSequence(' ').iterator()
    }
}
