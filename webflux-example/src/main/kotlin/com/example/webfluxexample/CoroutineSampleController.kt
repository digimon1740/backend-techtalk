package com.example.webfluxexample

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.slf4j.MDC
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toMono
import java.time.Duration
import java.util.UUID

@RestController
class CoroutineSampleController(
    val repo: ReactiveUserRepository,
    val userRepository: CoroutineUserRepository,
    val pointRepository: CoroutinePointRepository,
) {

    val logger = KotlinLogging.logger { }

    @GetMapping("/reactor/users/{userId}")
    fun getMono(
        @PathVariable("userId") id: Long = 0,
        @RequestParam(required = false) delay: Long? = null
    ): Mono<UserResponse> {
        return repo.findById(id)
            .switchIfEmpty { repo.save(UserEntity(name = "Captain America")) }
            .flatMap {
                UserResponse(id = it.id, name = it.name).toMono()
            }
    }

    @GetMapping("/reactor/users", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getsFlux(@RequestParam delay: Long = 0): Flux<UserResponse> {
        return Flux.range(1, Int.MAX_VALUE)
            .map { UserResponse(id = it.toLong(), name = "Captain America-$it") }
            .delayElements(Duration.ofMillis(delay))
    }

    @GetMapping("/coroutine/users/{userId}")
    suspend fun get(
        @PathVariable("userId") id: Long = 0,
        @RequestParam(required = false) delay: Long? = null
    ): UserResponse {
        val user = userRepository.findById(id) ?: userRepository.save(UserEntity(name = "Captain America"))
        return UserResponse(id = user.id, name = user.name)
    }

    @GetMapping("/coroutine/users", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun gets(@RequestParam delay: Long = 0): Flow<UserResponse> {
        return flow {
            var id = 0L
            while (true) {
                id++
                emit(UserResponse(id = id, name = "Captain America-$id"))
                delay(delay)
            }
        }
    }



    @GetMapping("/users/{userId}/order")
    suspend fun getUserAndOrder(
        @PathVariable("userId") userId: Long,
    ) {
        MDC.put("requestId", UUID.randomUUID().toString())

        logger.info { "call remote api" }

        coroutineScope {
            val user = async(Dispatchers.IO) {
                logger.info { "call user api" }
                getUser(userId)
            }

            val order = async(Dispatchers.IO) {
                logger.info { "call order api" }
                getOrder(userId)
            }

            UserOrderResponse(
                user = user.await(),
                order = order.await()
            )
        }

    }


    fun getUser(id: Long): UserResponse {
        return UserResponse(id = id, name = "Captain America")
    }

    fun getOrder(id: Long): OrderResponse {
        return OrderResponse(id = id, name = "Captain America")
    }
}

data class OrderResponse(val id: Long, val name: String)

data class UserOrderResponse(
    val user: UserResponse,
    val order: OrderResponse
)


//[reactor-http-nio-2] INFO  [requestId=bc5cb224-5d0b-4643-bf05-10625605155f] get user and order data
//[DefaultDispatcher-worker-1] INFO  [requestId=bc5cb224-5d0b-4643-bf05-10625605155f] call user api
//[DefaultDispatcher-worker-1] INFO  [requestId=bc5cb224-5d0b-4643-bf05-10625605155f] call order api


//[reactor-http-nio-2]         INFO  [requestId=e2a75398-3b39-48f3-b5a2-57aeb9ec53a5] call remote api
//[DefaultDispatcher-worker-1] INFO  [requestId=] call user api
//[DefaultDispatcher-worker-1] INFO  [requestId=] call order api