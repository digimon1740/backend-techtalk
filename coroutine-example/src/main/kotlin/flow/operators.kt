package flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    println("filterExample()")
    filterExample()
    println("=====================================")

    println("takeExample()")
    takeExample()
    println("=====================================")

    println("mapExample()")
    mapExample()
    println("=====================================")

    println("zipExample()")
    zipExample()
    println("=====================================")

    println("flattenExample()")
    flattenExample()
    println("=====================================")

}

suspend fun filterExample() {
    (1..10)
        .asFlow()
        .filter { (it % 2) == 0 }
        .collect {
            println(it)
        }
}

suspend fun takeExample() {
    (1..10)
        .asFlow()
        .take(2)
        .collect {
            println(it)
        }
}

suspend fun mapExample() {
    (1..10)
        .asFlow()
        .map { it * it }
        .collect {
            println(it)
        }
}

suspend fun zipExample() {
    val nums = (1..3).asFlow()
    val strs = listOf("one", "two", "three").asFlow()

    nums.zip(strs) { a, b -> "$a -> $b" }
        .collect { println(it) }
}


suspend fun flattenExample() {
    val left = flowOf(1, 2, 3)
    val right = flowOf(4, 5, 6)
    val flow = flowOf(left, right)

    flow.flattenConcat().collect { println(it) }
}
