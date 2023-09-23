package no.tidya.openai.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatCompletionRequest(
    val model: String, val messages: List<Message>
)
