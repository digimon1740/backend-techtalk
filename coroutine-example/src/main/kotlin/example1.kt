import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

fun main() = runBlocking {
    repeat(1_000_000) { // launch a lot of coroutines
        launch {
            delay(5000L)
            print(".")
        }
    }
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

