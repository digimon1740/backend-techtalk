package flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    flowOf(1, 2, 3, 4, 5).collect { value ->
        println(value)
    }
}