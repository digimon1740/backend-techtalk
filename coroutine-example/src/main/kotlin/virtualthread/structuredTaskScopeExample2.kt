package virtualthread

import kotlinx.coroutines.*
import kotlinx.coroutines.slf4j.MDCContext
import java.util.concurrent.Executors
import java.util.concurrent.StructuredTaskScope
import java.util.concurrent.StructuredTaskScope.Subtask
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


fun main() = runBlocking<Unit> {
    //sendMessage1()
    sendMessage()
}

fun sendMessage1() {
    StructuredTaskScope.ShutdownOnFailure().use { scope ->
        scope.fork { sendEmail() }
        scope.fork { sendSms() }

        scope.join()

        scope.throwIfFailed { e ->
            RuntimeException("Failed to send", e)
        }
    }
}

fun sendMessage() = runBlocking (Dispatchers.VIRTUAL_THREAD) {
    launch { sendEmail() }

    launch { sendSms() }
}

fun sendEmail() {
    // .. 이메일 발송 구현
    println("이메일 발송 완료")
}

fun sendSms() {
    // .. SMS 발송 구현
    println("SMS 발송 완료")
}

//
//
//fun main() = runBlocking<Unit> {
//    sendMessageCoroutine()
//}
//
//suspend fun sendMessageCoroutine() = coroutineScope {
//    launch { sendEmail() }
//    launch { sendSms() }
//}
//
val Dispatchers.VIRTUAL_THREAD: ExecutorCoroutineDispatcher
    get() {
        println(1111)
        return Executors.newVirtualThreadPerTaskExecutor().asCoroutineDispatcher()
    }

fun <T> CoroutineScope.asyncOnVirtualThread(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T,
): Deferred<T> {
    return async(Dispatchers.VIRTUAL_THREAD + context, start, block)
}

fun CoroutineScope.launchOnVirtualThread(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit,
): Job {
    return launch(Dispatchers.VIRTUAL_THREAD + context, start, block)
}