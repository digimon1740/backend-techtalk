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

    launch {
        try {
            throw RuntimeException()
        } catch (e: Exception) {
            println("hello")
        }
    }
}