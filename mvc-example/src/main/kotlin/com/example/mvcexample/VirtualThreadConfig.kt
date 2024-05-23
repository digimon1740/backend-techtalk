import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

//package com.example.mvcexample
//
//import org.apache.coyote.ProtocolHandler
//import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration
//import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.core.task.AsyncTaskExecutor
//import org.springframework.core.task.support.TaskExecutorAdapter
//import org.springframework.scheduling.annotation.EnableAsync
//import java.util.concurrent.Executors
//
//
//@EnableAsync
//@Configuration
//class VirtualThreadConfig {
//
//    @Bean(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME)
//    fun asyncTaskExecutor(): AsyncTaskExecutor {
//        return TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor())
//    }
//
//    @Bean
//    fun protocolHandlerVirtualThreadExecutorCustomizer(): TomcatProtocolHandlerCustomizer<*> {
//        return TomcatProtocolHandlerCustomizer { protocolHandler: ProtocolHandler ->
//            protocolHandler.executor = Executors.newVirtualThreadPerTaskExecutor()
//        }
//    }
//}

val VIRTUAL_THREAD = Executors.newVirtualThreadPerTaskExecutor().asCoroutineDispatcher()

