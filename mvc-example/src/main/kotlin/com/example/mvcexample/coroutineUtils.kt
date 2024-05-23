package com.example.mvcexample

import kotlinx.coroutines.*
import kotlinx.coroutines.slf4j.MDCContext
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


fun main() = runBlocking<Unit> {

    launch(Dispatchers.VIRTUAL_THREAD) {
        println("Hello, Virtual Thread!")
    }

    launch(Dispatchers.VIRTUAL_THREAD) {
        println("Hello, Virtual Thread!")
    }
}

val Dispatchers.VIRTUAL_THREAD: ExecutorCoroutineDispatcher
    get() = Executors.newVirtualThreadPerTaskExecutor().asCoroutineDispatcher()

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