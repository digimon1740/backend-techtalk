package virtualthread

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import java.util.concurrent.StructuredTaskScope
import java.util.concurrent.StructuredTaskScope.Subtask


fun main() {
    getUsers1().forEach {
        println(it)
    }
}

fun getUsers1(): List<UserResponse> {
    StructuredTaskScope.ShutdownOnFailure().use { scope ->
        val fork1: Subtask<UserResponse> = scope.fork { callApi(1) }
        val fork2: Subtask<UserResponse> = scope.fork { callApi(2) }

        scope.join()
        scope.throwIfFailed { e ->
            RuntimeException("Failed to get user", e)
        }

        return listOf(fork1.get(), fork2.get())
    }
}

suspend fun getUsers(): List<UserResponse> = coroutineScope {
    val user1 = async { callApi(1) }
    val user2 = async { callApi(2) }

    listOf(user1.await(), user2.await())
}
//
//fun getUsers() = runBlocking {
//    val user1 = async { callApi(1) }
//    val user2 = async { callApi(2) }
//
//    listOf(user1.await(), user2.await())
//}





fun callApi(id: Long): UserResponse {
    val name = if (id == 1L) {
        "아이언맨"
    } else {
        throw RuntimeException("User not found")
    }
    return UserResponse(id, name)
}

data class UserResponse(
    val id: Long,
    val name: String,
)