package no.tidya.openai.model

import kotlinx.serialization.Serializable

@Serializable
data class Usage(
    val prompt_tokens: Int, val completion_tokens: Int, val total_tokens: Int
)
