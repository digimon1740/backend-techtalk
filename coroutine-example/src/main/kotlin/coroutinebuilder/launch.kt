package coroutinebuilder

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {

    example1()
//    example2()
//    example3()
}



private fun example1() = runBlocking<Unit> {
    launch {
        delay(500L)
        println("World!")
    }
    println("Hello")
}

private fun example2() {
    runBlocking<Unit> {


        launch {
            val timeMillis = measureTimeMillis {
                delay(150)
            }
            println("async task-1 $timeMillis ms")
        }

        launch {
            val timeMillis = measureTimeMillis {
                delay(100)
            }
            println("async task-2 $timeMillis ms")
        }

    }
}

private fun example3() = runBlocking<Unit> {
    val job1: Job = launch {
        val timeMillis = measureTimeMillis {
            delay(150)
        }
        println("async task-1 $timeMillis ms")
    }
    job1.cancel() // 취소


    val job2: Job = launch(start = CoroutineStart.LAZY) {
        val timeMillis = measureTimeMillis {
            delay(100)
        }
        println("async task-2 $timeMillis ms")
    }

    println("start task-2")

    job2.start()

}