package com.example.mockserver

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener

@SpringBootApplication
class MockServerApplication {

    val logger = KotlinLogging.logger { }

    @EventListener(ApplicationReadyEvent::class)
    fun warmUp() {
        logger.info { "Warm up start" }
        for (i in 0..99999) {
            val dummy = Dummy()
            dummy.m()
        }
        logger.info { "Warm up end" }
    }
}

class Dummy {
    fun m() {
        val a = 1
        val b = 2
        val c = a + b
    }
}

fun main(args: Array<String>) {
    runApplication<MockServerApplication>(*args)
}
