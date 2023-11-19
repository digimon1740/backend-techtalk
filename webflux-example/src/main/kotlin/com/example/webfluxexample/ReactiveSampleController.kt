package com.example.webfluxexample

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@RestController
class ReactiveSampleController(
    val webClient: WebClient,
) {

    val logger = KotlinLogging.logger { }

    @GetMapping("/webflux/users/{userId}")
    fun get(
        @PathVariable("userId") id: Long = 0,
        @RequestParam(required = false) delay: Long? = null
    ): Mono<UserResponse> {
        return webClient
            .get()
            .uri("http://localhost:9090/mock/users/$id?delay=$delay")
            .retrieve()
            .bodyToMono(UserResponse::class.java)
    }
}
