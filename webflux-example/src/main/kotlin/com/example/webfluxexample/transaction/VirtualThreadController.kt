package com.example.webfluxexample.transaction

import com.example.webfluxexample.PointEntity
import com.example.webfluxexample.PointRepository
import com.example.webfluxexample.UserRepository
import com.example.webfluxexample.UserResponse
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.StructuredTaskScope

@RestController
class VirtualThreadController(
    private val userRepository: UserRepository,
    private val pointRepository: PointRepository,
) {

    @PostMapping("/users/{userId}/point")
    @Transactional
    fun givePoint(@PathVariable userId: Long): UserResponse {

        StructuredTaskScope.ShutdownOnFailure().use { scope ->
            val pointTask = scope.fork {
                pointRepository.save(PointEntity(userId = userId, point = 100))
            }
            val userTask = scope.fork {
                Thread.sleep(500)
                throw RuntimeException("error")
                userRepository.findById(userId)
            }

            scope.join()
            scope.throwIfFailed()

            val user = userTask.get()
            val point = pointTask.get()
            return UserResponse(id = user.id, name = user.name, point = point.point)
        }
    }

}