package com.example.webfluxexample

import kotlinx.coroutines.runBlocking
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository


interface CoroutinePointRepository : CoroutineCrudRepository<PointEntity, Long>

@Repository
class PointRepository(
    private val repository: CoroutinePointRepository
) {

    fun save(point: PointEntity): PointEntity = runBlocking {
        repository.save(point)
    }

    fun findById(id: Long): PointEntity? = runBlocking {
        repository.findById(id)
    }
}

@Table("points")
data class PointEntity(
    @Id val id: Long = 0,
    val userId: Long,
    val point: Long,
)


