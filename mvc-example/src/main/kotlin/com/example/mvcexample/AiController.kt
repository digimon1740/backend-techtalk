package com.example.mvcexample

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.boot.web.client.ClientHttpRequestFactories
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestClient
import java.time.Duration

@RestController
class AiController(
    private val aiClient: AiClient,
) {

    val logger = KotlinLogging.logger { }

    @GetMapping("/ask")
    suspend fun ask(): AskResponse {
        logger.info { "askRequest" }
        val request = AskRequest("인생이란 무엇일까?")
        return aiClient.ask(request)
    }
}

@Component
class AiClient(
    private val aiRestClient: RestClient,
) {

    val limitedPool = Dispatchers.IO.limitedParallelism(200)

    suspend fun ask(request: AskRequest) = withContext(limitedPool) {
        val response = aiRestClient.post()
            .uri("/gpt/ask")
            .body(request)
            .retrieve()
            .body(AskResponse::class.java)

        response ?: AskResponse(answer = "No answer")
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
}


//suspend fun ask(request: AskRequest): AskResponse = withContext(Dispatchers.IO) {
//    val response = aiRestClient.post()
//        .uri("/gpt/ask")
//        .body(request)
//        .retrieve()
//        .body(AskResponse::class.java)
//
//    response ?: AskResponse(answer = "No answer")
//}