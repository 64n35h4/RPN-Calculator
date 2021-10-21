package services


import org.junit.jupiter.api.Assertions.*
import org.koin.test.KoinTest
import kotlin.test.Test

class ParserServiceTest: KoinTest {
    private val parserService = ParserService()

    @Test
    fun init() {
        parserService.init("6 4")
        assertEquals(parserService.userInput, "6 4")
    }

    @Test
    fun parse() {
        parserService.init("6 4")
        val parsed = parserService.parse()
        assertEquals(parsed.asSequence().toList(), listOf("6", "4"))
    }
}