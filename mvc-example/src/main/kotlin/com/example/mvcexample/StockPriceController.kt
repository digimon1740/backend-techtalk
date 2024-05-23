package com.example.mvcexample

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.Executors
import kotlin.math.log
import kotlin.math.sin
import kotlin.random.Random

@RestController
class StockPriceController(
    private val stockPriceClient: StockPriceClient,
) {


    @GetMapping("/price/{symbol}/stream", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun findPrice(
        @PathVariable symbol: String,
    ): Flow<StockPriceResponse> {
        return flow {
            while (true) {
                val response = stockPriceClient.findPrice(symbol)
                emit(response)
            //    delay(500)
            }
        }.flowOn(Dispatchers.VIRTUAL_THREAD)
    }

}

@Component
class StockPriceClient {

    fun findPrice(symbol: String): StockPriceResponse {
        // 150.0 ~ 170.0 사이의 랜덤한 주식 가격을 생성합니다. 이때 소수점은 둘째 자리까지만 표시합니다.
        Thread.sleep(2000)
        return StockPriceResponse(symbol, (Random.nextDouble(150.0, 170.0) * 100).toInt() / 100.0)
    }
}

data class StockPriceResponse(
    val symbol: String = "",
    val price: Double = 0.0,
)

