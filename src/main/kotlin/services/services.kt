package services

import org.koin.dsl.module

// defining singleton services
val services = module {
    single { ValueStackService() }
    single { ActionStackService() }
    single { ParserService() }
}

// interface to be shared by Collection data structures
interface ICollectionService<T> {
    fun init()
    fun push(value: T)
    fun pop(): T
    fun clear()
    fun display()
    fun isEmpty(): Boolean
    fun size(): Int
}

// interface to be shared by Parser data services
interface IParserService {
    fun init(raw_input: String?)
    fun parse(): Iterator<String>?
}

// Utility helpers for Action Stack (for undo/redo actions)
interface IAction {
    val actionType: String
    val actionPayload: List<Double>
}

data class Action(
    override val actionType: String,
    override val actionPayload: List<Double>
) : IAction
