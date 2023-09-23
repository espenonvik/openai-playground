package no.tidya.openai.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatCompletionResponse(
    val id: String, val `object`: String, val created: Long, val model: String, val choices: List<Choice>, val usage: Usage
)
