package structuredconcurrency

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import reactor.core.publisher.Mono
import kotlin.concurrent.thread

suspend fun main() = coroutineScope<Unit> {

    launch {
        delay(200)
        println("예외가 발생해도 출력!")

    }

    supervisorScope {
        launch {
            throw RuntimeException()
        }
    }
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