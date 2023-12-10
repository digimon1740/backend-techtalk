package coroutinebuilder

import kotlinx.coroutines.*

fun main() {

    runBlocking {
        println("Hello")
    }
    println("World")
}