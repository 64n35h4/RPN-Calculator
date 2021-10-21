package services

import java.util.Stack

class ValueStackService : ICollectionService<Double> {
    private lateinit var stack: Stack<Double>

    override fun init() {
        this.stack = Stack<Double>()
    }

    override fun push(value: Double) {
        this.stack.push(value)
    }

    override fun pop(): Double {
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

    override fun display() {
        println("Current Stack: ${this.stack}")
    }
}
