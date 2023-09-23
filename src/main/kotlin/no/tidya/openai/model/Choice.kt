package no.tidya.openai.model

import kotlinx.serialization.Serializable

@Serializable
data class Choice(
    val index: Int, val message: Message, val finish_reason: String
)
