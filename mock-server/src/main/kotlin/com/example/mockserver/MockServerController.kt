package com.example.mockserver

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicInteger

@RestController
class MockServerController {

    val logger = KotlinLogging.logger { }

    val mvcCounter = AtomicInteger(0)
    val webfluxCounter = AtomicInteger(0)

    @GetMapping("/mock/users/{userId}")
    suspend fun get(
        @PathVariable("userId") userId: Long,
        @RequestParam("delay") delay: Long? = null,
        @RequestParam(required = false, name = "blocking") blocking: Boolean? = null,
    ): UserResponse = coroutineScope {
        delay(delay ?: 0)
        if (blocking == true) {
            logger.info { "MVC-RequestCount : ${mvcCounter.incrementAndGet()}" }
        } else {
            logger.info { "WebFlux-RequestCount : ${webfluxCounter.incrementAndGet()}" }
        }
        UserResponse(id = userId, name = "Captain America")
    }
}

//echo 'GET http://localhost:8080/mvc/users/1?delay=2000' | vegeta attack -duration=10s -rate=300/s | vegeta report
//echo 'GET http://localhost:8081/webflux/users/1?delay=2000' | vegeta attack -duration=10s -rate=300/s | vegeta report
data class UserResponse(
    val id: Long,
    val name: String,
)


