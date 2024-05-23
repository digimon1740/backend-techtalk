package com.example.webfluxexample

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.boot.web.client.ClientHttpRequestFactories
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestClient
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitBodyOrNull
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration

@RestController
class AiController(
    private val aiClient: AiClient,
) {

    @GetMapping("/ask")
    suspend fun ask(): AskResponse {
        val request = AskRequest("인생이란 무엇일까?")
        return aiClient.ask(request)
    }
}

@Component
class AiClient(
    private val aiWebClient: WebClient,
) {

    suspend fun ask(request: AskRequest): AskResponse {
        val response = aiWebClient.post()
            .uri("/gpt/ask")
            .bodyValue(request)
            .retrieve()
            .awaitBodyOrNull<AskResponse>()

        return response ?: AskResponse(answer = "No answer")
    }
}

data class AskRequest(
    val payload: String,
)

data class AskResponse(
    val answer: String,
)

@Configuration
class AiConfig {

    @Bean
    fun aiRestClient(): RestClient {
        val settings = ClientHttpRequestFactorySettings
            .DEFAULTS
            .withReadTimeout(Duration.ofSeconds(60))
            .withConnectTimeout(Duration.ofSeconds(60))

        val factory = ClientHttpRequestFactories.get(settings)
        return RestClient.builder()
            .baseUrl("http://localhost:9090")
            .requestFactory(factory)
            .build()
    }

    @Bean
    fun aiWebClient(): WebClient {
        val connectionProvider = ConnectionProvider.builder("myConnectionPool")
            .maxConnections(1000)
            .pendingAcquireMaxCount(10000)
            .build()
        val clientHttpConnector = ReactorClientHttpConnector(HttpClient.create(connectionProvider))
        return WebClient
            .builder()
            .baseUrl("http://localhost:9090")
            .clientConnector(clientHttpConnector)
            .build()
    }
}



