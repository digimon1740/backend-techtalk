package structuredconcurrency

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlin.concurrent.thread
import kotlin.jvm.Throws

//fun main () {
//    val thread = thread {
//        Thread.sleep(1000)
//        println("World")
//    }
//
//    println("Hello")
//
//    thread.interrupt()
//
//    println("Done!")
//}
//
//suspend fun main() = coroutineScope {
//    val job = launch {
//        throw Exception()
//    }
//
//    println("Hello")
//
//    job.cancel()
//
//    println("Done!")
//}
//
