package com.example.webfluxexample

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toMono
import java.time.Duration

@RestController
class CoroutineSampleController(
    val repo: UserRepository,
    val coroutineRepo: CoroutineUserRepository,
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
        val user = coroutineRepo.findById(id) ?: coroutineRepo.save(UserEntity(name = "Captain America"))
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
}
