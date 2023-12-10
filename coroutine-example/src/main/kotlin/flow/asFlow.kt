package flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    listOf(1, 2, 3, 4, 5).asFlow().collect { value ->
        println(value)
    }
}