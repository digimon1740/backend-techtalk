package structuredconcurrency

import kotlinx.coroutines.*

 fun main() = runBlocking<Unit> {

    doSomething()

}

private suspend fun doSomething() = coroutineScope {

    withContext(NonCancellable) {
        launch {
            delay(200)
            //throw RuntimeException()
            println("예외가 발생해도 출력!")
        }
        throw RuntimeException()
    }

    launch {
        throw RuntimeException()
    }

}