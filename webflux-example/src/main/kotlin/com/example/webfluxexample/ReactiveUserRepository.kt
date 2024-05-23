package com.example.webfluxexample

import kotlinx.coroutines.runBlocking
import org.springframework.data.annotation.Id
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository


interface ReactiveUserRepository : R2dbcRepository<UserEntity, Long>

interface CoroutineUserRepository : CoroutineCrudRepository<UserEntity, Long>

@Repository
class UserRepository(
    private val repository: CoroutineUserRepository
) {

    fun save(user: UserEntity): UserEntity = runBlocking { repository.save(user) }

    fun findById(id: Long): UserEntity = runBlocking {
        //repository.findById(id)!!
        throw RuntimeException("error")
        UserEntity(1, "test")
    }
}


@Table("users")
data class UserEntity(
    @Id val id: Long = 0,
    val name: String,
)

data class UserResponse(
    val id: Long,
    val name: String,
    val point: Long = 0,
)


