package structuredconcurrency

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun main() {
    doSomething()
}

private suspend fun doSomething() = coroutineScope {

    launch {
        delay(200)
        println("world!")

    }

    launch {
        println("hello")
    }

}