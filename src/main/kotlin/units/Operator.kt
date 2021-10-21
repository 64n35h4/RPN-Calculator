package units

import units.operations.* // ktlint-disable no-wildcard-imports
import utils.OperationNotFound
import kotlin.collections.HashMap

enum class OPERATIONS {
    ADD,
    SUB,
    MUL,
    DIV,
    SQT
}

class Operator {
    companion object {
        val enumMapper = HashMap<OPERATIONS, IOperation>()
        private var symbolMapper = HashMap<String, OPERATIONS>()

        init {
            enumMapper[OPERATIONS.ADD] = AddLogic()
            enumMapper[OPERATIONS.SUB] = SubLogic()
            enumMapper[OPERATIONS.MUL] = MulLogic()
            enumMapper[OPERATIONS.DIV] = DivLogic()
            enumMapper[OPERATIONS.SQT] = SqrtLogic()
            symbolMapper = OPERATIONS.values().associateBy { enumMapper[it]!!.symbol } as HashMap<String, OPERATIONS>
        }

        // method to get all math operations
        fun getPossibleOPS(): Set<String> {
            return symbolMapper.keys
        }

        // method to get an operation enum from an operation symbol (i.e: '+' -> ADD)
        fun fromSymbol(symbol: String): IOperation {
            val operationEnum = symbolMapper[symbol] ?: throw OperationNotFound()
            return enumMapper[operationEnum]!!
        }

        // method to get an operation enum from an operation name (i.e: 'ADD' -> ADD)
        fun getOperatorByEnumString(op: String): IOperation {
            if (!OPERATIONS.values().map { it.name }.contains(op.uppercase())) {
                throw OperationNotFound()
            }
            return this.enumMapper[OPERATIONS.valueOf(op.uppercase())]!!
        }
    }
}
