import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject
import services.ParserService
import services.services
import services.ValueStackService
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class RPNTest : KoinTest {
    private val stackServiceA by inject<ValueStackService>()
    private val parserServiceA by inject<ParserService>()

    @Before
    fun setUp() {
        startKoin {
            printLogger()
            modules(services)
        }
    }

    @After
    fun stopDependencies() {
        stopKoin()
    }

    @Test
    fun `should inject my components`() {
        val stackServiceB = get<ValueStackService>()

        assertNotNull(stackServiceB)
        assertEquals(stackServiceB, stackServiceA)
    }

    @Test
    fun `Testing Parser`() {
        val parserServiceB = get<ParserService>()

        assertNotNull(parserServiceB)
        assertEquals(parserServiceB, parserServiceA)
    }
}
