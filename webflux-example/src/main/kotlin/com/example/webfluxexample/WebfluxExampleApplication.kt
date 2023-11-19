package com.example.webfluxexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider


@SpringBootApplication
class WebfluxExampleApplication {

    @Bean
    fun webClient(): WebClient {
        val connectionProvider = ConnectionProvider.builder("myConnectionPool")
            .maxConnections(1000)
            .pendingAcquireMaxCount(10000)
            .build()
        val clientHttpConnector = ReactorClientHttpConnector(HttpClient.create(connectionProvider))
        return WebClient
            .builder()
            .clientConnector(clientHttpConnector)
            .build()
    }
}

fun main(args: Array<String>) {
    runApplication<WebfluxExampleApplication>(*args)
}
