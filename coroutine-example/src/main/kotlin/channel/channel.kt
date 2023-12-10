package channel

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import kotlin.random.Random

fun main() = runBlocking {
    val channel: Channel<String> = Channel()

    // 생산자와 소비자 코루틴을 실행
    producer(channel, 20)
    consumer(channel)
}


suspend fun CoroutineScope.producer(channel: Channel<String>, count: Int) {
    launch {
        repeat(count) {
            val stockPrice = "APPLE : ${Random.nextInt(100, 200)}"
            channel.send(stockPrice)
            delay(100L) // 0.1초 간격으로 데이터 생성
        }
        channel.close()  // 모든 데이터를 보냈으면 채널을 닫습니다.
    }
}

suspend fun consumer(channel: Channel<String>) {
    for (stockPrice in channel) {
        println("Processed $stockPrice")
    }
}
