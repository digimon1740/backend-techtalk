package channel

import kotlinx.coroutines.*
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import kotlin.random.Random

fun main() = runBlocking {
    val queue: BlockingQueue<String> = LinkedBlockingQueue()


    // 생산자와 소비자 코루틴을 실행
    producer(queue, 20)
    consumer(queue)
}

suspend fun producer(queue: BlockingQueue<String>, count: Int) = coroutineScope {
    launch {
        repeat(count) {
            val stockPrice = "APPLE : ${Random.nextInt(100, 200)}"
            queue.put(stockPrice)
            delay(100L) // 0.1초 간격으로 데이터 생성
        }
    }
}

fun consumer(queue: BlockingQueue<String>) {
    for (stockPrice in queue) {
        println("Processed $stockPrice")
    }
}