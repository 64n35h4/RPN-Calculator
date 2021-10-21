package services

import java.util.Stack

class ActionStackService : ICollectionService<Action> {
    private lateinit var stack: Stack<Action>

    override fun init() {
        this.stack = Stack<Action>()
    }

    override fun push(value: Action) {
        this.stack.push(value)
    }

    override fun pop(): Action {
        return this.stack.pop()
    }

    override fun clear() {
        this.stack.clear()
    }

    override fun size(): Int {
        return this.stack.size
    }

    override fun isEmpty(): Boolean {
        return this.stack.isEmpty()
    }

    // function specific for ActionStack
    fun getLastAction(): String {
        return this.stack.peek().actionType
    }

    override fun display() {
        println("Current Action Stack: ${this.stack}")
    }
}
