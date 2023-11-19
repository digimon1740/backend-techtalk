package com.example.mvcexample

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class SampleController {

    val logger = KotlinLogging.logger { }

    @GetMapping("/mvc/users/{userId}")
    fun get(
        @PathVariable("userId") id: Long = 0,
        @RequestParam(required = false) delay: Long? = null
    ): UserResponse {
        return RestTemplate()
            .getForObject("http://localhost:9090/mock/users/$id?delay=$delay&blocking=true", UserResponse::class.java)!!
    }
}

data class UserResponse(
    val id: Long,
    val name: String,
)



