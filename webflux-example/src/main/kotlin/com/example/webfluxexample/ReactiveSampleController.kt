//package com.example.webfluxexample
//
//import io.github.oshai.kotlinlogging.KotlinLogging
//import kotlinx.coroutines.async
//import kotlinx.coroutines.coroutineScope
//import org.springframework.stereotype.Component
//import org.springframework.stereotype.Service
//import org.springframework.web.bind.annotation.*
//import org.springframework.web.reactive.function.client.WebClient
//import org.springframework.web.reactive.function.client.awaitBody
//import reactor.core.publisher.Mono
//import reactor.core.scheduler.Schedulers
//import reactor.kotlin.core.publisher.toMono
//import reactor.util.function.Tuple2
//import reactor.util.function.Tuple3
//import java.util.concurrent.CompletableFuture
//
//@RestController
//class ReactiveSampleController(
//    val webClient: WebClient,
//    val userService: UserService,
//) {
//
//    val logger = KotlinLogging.logger { }
//
//    @GetMapping("/webflux/users/{userId}")
//    fun get(
//        @PathVariable("userId") id: Long = 0,
//        @RequestParam(required = false) delay: Long? = null
//    ): Mono<UserResponse> {
//        return webClient
//            .get()
//            .uri("http://localhost:9090/mock/users/$id?delay=$delay")
//            .retrieve()
//            .bodyToMono(UserResponse::class.java)
//    }
//
//
//    @GetMapping("/users/{userId}")
//    fun getUserResponse(
//        @PathVariable("userId") userId: Long,
//    ): Mono<UserCompositeResponse> =
//        Mono.zip(
//            getUser(userId),
//            getFollower(userId),
//        ).map { (user, follower) ->
//            UserCompositeResponse(
//                user = user,
//                follower = follower,
//            )
//        }
//
//    fun getUser(userId: Long): Mono<UserResponse> =
//        webClient
//            .get()
//            .uri("http://localhost:9090/api/users/${userId}")
//            .retrieve()
//            .bodyToMono(UserResponse::class.java)
//
//
//    fun getFollower(userId: Long): Mono<FollowerResponse> =
//        webClient
//            .get()
//            .uri("http://localhost:9090/api/users/${userId}/follower")
//            .retrieve()
//            .bodyToMono(FollowerResponse::class.java)
//
//
//    @GetMapping("/users/{userId}")
//    suspend fun getUserResponseAwait(
//        @PathVariable("userId") userId: Long,
//    ): UserCompositeResponse = coroutineScope {
//
//        val user = async { getUser(userId) }
//        val follower = async { getFollower(userId) }
//
//        UserCompositeResponse(user.await(), follower.await())
//    }
//
//    suspend fun getUser(userId: Long): UserResponse =
//        webClient
//            .get()
//            .uri("http://localhost:9090/api/users/${userId}")
//            .retrieve()
//            .awaitBody()
//
//
//    suspend fun getFollower(userId: Long): FollowerResponse =
//        webClient
//            .get()
//            .uri("http://localhost:9090/api/users/${userId}/follower")
//            .retrieve()
//            .awaitBody()
//}
//
//
//@Service
//class UserService(
//    private val userJpaRepository: UserJpaRepository,
//) {
//
//    fun getUserBlocking(userId: Long): Mono<User> {
//        return userJpaRepository.findById(userId).toMono()
//    }
//
//    fun getUser(userId: Long): Mono<User> {
//        return Mono.fromCallable {
//            userJpaRepository.findById(userId)
//        }.subscribeOn(Schedulers.boundedElastic())
//    }
//
//}
//
//@Component
//class UserJpaRepository {
//
//    fun findById(id: Long): User {
//        return User(id, "Captain America")
//    }
//}
//
//data class User(
//    val id: Long,
//    val name: String,
//)
//
//private operator fun <T1, T2, T3> Tuple3<T1, T2, T3>.component3(): FollowerResponse {
//    TODO("Not yet implemented")
//}
//
//
//private operator fun <T1, T2> Tuple2<T1, T2>.component2(): FollowerResponse {
//    TODO("Not yet implemented")
//}
//
//private operator fun <T1, T2> Tuple2<T1, T2>.component1(): UserResponse {
//    TODO("Not yet implemented")
//}
//
//data class UserCompositeResponse(
//    val user: UserResponse,
//    val follower: FollowerResponse,
//)
//
//data class LikeResponse(
//    val id: Long,
//    val like: String,
//)
//
//data class FollowerResponse(
//    val id: Long,
//    val follow: String,
//)