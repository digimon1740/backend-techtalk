//import org.junit.jupiter.api.Test
//import reactor.core.publisher.Mono
//import reactor.core.scheduler.Schedulers
//import reactor.kotlin.core.publisher.toMono
//
//class BlockTest {
//
//
//    @Test
//    fun block() {
//        val userService = UserService(UserJpaRepository())
//        val user = userService.getUser(1)
//
//        user.subscribe(::println)
//        Thread.sleep(2000)
//    }
//}
//
//class UserService(
//    private val userJpaRepository: UserJpaRepository,
//) {
//
//    fun getUserBlocking(userId: Long): Mono<User> {
//        return userJpaRepository.findById(userId).toMono()
//    }
//
//    fun getUser(userId: Long): Mono<User> {
//        println("getUser thread : ${Thread.currentThread().name}")
//        val userMono =
//        Mono.fromCallable {
//            userJpaRepository.findById(userId)
//        }.subscribeOn(Schedulers.boundedElastic())
//
//
//        return userMono
//    }
//
//}
//
//class UserJpaRepository {
//
//    fun findById(id: Long): User {
//        Thread.sleep(1000)
//        println("findById thread : ${Thread.currentThread().name}")
//        return User(id, "Captain America")
//    }
//}
//
//data class User(
//    val id: Long,
//    val name: String,
//)
