package utils

class IncorrectArgsException(message: String = "Incorrect Supplied Number of Args to Method") : Exception(message)

class InsufficientParamsException(message: String = "Insufficient Params") : Exception(message)

class CommandNotFound(message: String = "Provided Command Not Found") : Exception(message)

class OperationNotFound(message: String = "Provided Operation Not Found") : Exception(message)
