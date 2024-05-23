package structuredconcurrency

import kotlinx.coroutines.*

suspend fun main() {
    doSomething()
}

private suspend fun doSomething() = coroutineScope {

    launch {
        delay(200)
        println("world!")

    }

    supervisorScope {
        launch(handler) {
            throw RuntimeException()
        }
    }

}

val handler = CoroutineExceptionHandler { _, throwable ->
    println("Caught ${throwable.stackTraceToString()}")
}