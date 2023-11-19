package com.example.webfluxexample

import org.springframework.data.annotation.Id
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.kotlin.CoroutineCrudRepository


interface UserRepository : R2dbcRepository<UserEntity, Long>

interface CoroutineUserRepository : CoroutineCrudRepository<UserEntity, Long>



@Table("users")
data class UserEntity(
    @Id val id: Long = 0,
    val name: String,
)

data class UserResponse(
    val id: Long,
    val name: String,
)


