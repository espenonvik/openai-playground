package no.tidya.openai

import io.github.cdimascio.dotenv.Dotenv
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import no.tidya.openai.model.ChatCompletionRequest
import no.tidya.openai.model.ChatCompletionResponse
import no.tidya.openai.model.Message


suspend fun main(args : Array<String>) {
    if (args.isEmpty()) {
        println("Usage: ./gradlew run --args=\"'<message>'\"")
        return
    }

    val apiKey = fetchApiKey()
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        expectSuccess = true
    }

    val chatCompletionRequest = ChatCompletionRequest(
        model = "gpt-3.5-turbo", messages = listOf(
            Message(role = "system", content = "You are Arnold Schwarzenegger"),
            Message(role = "user", content = args[0])
        )
    )

    val response = client.post("https://api.openai.com/v1/chat/completions") {
        setBody(chatCompletionRequest)
        headers {
            append("Content-Type", "application/json")
            append("Authorization", "Bearer $apiKey")
        }
    }.body<ChatCompletionResponse>()

    println("Response ID: ${response.id}")
    println("Model used: ${response.model}")

    response.choices.forEach { choice ->
        println(choice.message.content)
    }

    client.close()
}

fun fetchApiKey(): String {
    val dotenv = Dotenv.load()
    return dotenv["OPENAI_API_KEY"] ?: throw Exception("OPENAI_API_KEY not set in .env file")
}
