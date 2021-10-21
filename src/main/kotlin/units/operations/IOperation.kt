package units.operations

interface IOperation {
    val symbol: String
    fun execute(vararg operands: Double)
    fun prep(): DoubleArray
    fun undo()
}
