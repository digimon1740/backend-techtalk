import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime
import kotlin.time.measureTimedValue


fun main() = runBlocking {
    val counter = AtomicInteger()
    repeat(1_000_000) {
        launch {
            counter.incrementAndGet()
            Thread.sleep(2000)
        }
    }
    delay(3000)
    println("totalCoroutines : ${counter.get()}")
}


//fun main() {
//    repeat(1_000_000) { // launch a lot of threads
//        thread {
//            Thread.sleep(5000L)
//            print(".")
//        }
//    }
//    Thread.sleep(10000L)
//}

//fun main() {
//    val counter = AtomicInteger()
//    repeat(1_000_000) {
//        thread {
//            counter.incrementAndGet()
//            Thread.sleep(2000)
//        }
//    }
//    Thread.sleep(3000)
//}


//fun main() =  runBlocking {
//    launch {
//        delay(500L)
//        println("World!")
//    }
//    println("Hello")
//}
//


//fun main() = runBlocking<Unit> {
//
//
//    launch {
//        val timeMillis = measureTimeMillis {
//            delay(150)
//        }
//        println("async task-1 $timeMillis ms")
//    }
//
//    launch {
//        val timeMillis = measureTimeMillis {
//            delay(100)
//        }
//        println("async task-2 $timeMillis ms")
//    }
//
//}
//

//fun main() {
//    val counter = AtomicInteger()
//    repeat(1_000_000) {
//        Thread.startVirtualThread {
//            counter.incrementAndGet()
//            Thread.sleep(1000000000000)
//        }
//    }
//    Thread.sleep(1000000000000)
//    println("totalVirtualThreads : ${counter.get()}")
//}
//
//fun main() {
//    val counter = AtomicInteger()
//    repeat(500) {
//        thread {
//            counter.incrementAndGet()
//            Thread.sleep(1000000000000)
//        }
//    }
//    Thread.sleep(1000000000000)
//    println("totalVirtualThreads : ${counter.get()}")
//}