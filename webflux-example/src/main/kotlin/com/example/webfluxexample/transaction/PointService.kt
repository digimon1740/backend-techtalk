package com.example.webfluxexample.transaction

import com.example.webfluxexample.PointEntity
import com.example.webfluxexample.PointRepository
import com.example.webfluxexample.UserRepository
import com.example.webfluxexample.UserResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PathVariable

@Service
class PointService(
    private val userRepository: UserRepository,
    private val pointRepository: PointRepository,
) {

    @Transactional
    suspend fun givePoint(@PathVariable userId: Long): UserResponse {
//        val pointTask = async {
//            pointRepository.save(PointEntity(userId = userId, point = 100))
//        }
//
//        val userTask = async {
//            //delay(500)
//            throw RuntimeException("error")
//            userRepository.findById(userId)
//        }
//
//        val user = userTask.await()
//        val point = pointTask.await()

        val point = pointRepository.save(PointEntity(userId = userId, point = 100))
        val user = userRepository.findById(userId)

        return UserResponse(id = user.id, name = user.name, point = point.point)
    }
}