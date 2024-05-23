package com.example.webfluxexample.transaction

import com.example.webfluxexample.PointEntity
import com.example.webfluxexample.PointRepository
import com.example.webfluxexample.UserRepository
import com.example.webfluxexample.UserResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.StructuredTaskScope

@RestController
class CoroutineController(
    private val pointService: PointService,
) {

    @PostMapping("/coroutine/users/{userId}/point")
    suspend fun givePoint(@PathVariable userId: Long): UserResponse =
        pointService.givePoint(userId)
}