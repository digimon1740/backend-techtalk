package com.example.mockserver

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@RestController
class MockAiController {

    @PostMapping("/gpt/ask")
    suspend fun ask(
        @RequestBody request: AskRequest,
    ): AskResponse = coroutineScope {
        delay(Duration.ofSeconds(20).toMillis())
        AskResponse(answer = "인생이란 단순히 태어나서 살다가 죽는 것이 아니라, 끊임없이 변화하고 성장하는 과정 그 자체라고 생각합니다. 우리 모두가 각자의 방식으로 인생의 의미를 찾아가는 것이 중요하다고 봅니다.")
    }
}

data class AskRequest(
    val payload: String,
)

data class AskResponse(
    val answer: String,
)