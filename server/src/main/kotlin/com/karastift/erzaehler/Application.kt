package com.karastift.erzaehler

import ai.koog.ktor.Koog
import ai.koog.ktor.KoogAgentsConfig
import ai.koog.ktor.llm
import ai.koog.prompt.dsl.prompt
import ai.koog.prompt.executor.clients.google.GoogleModels
import ai.koog.prompt.executor.clients.openai.OpenAIModels
import ai.koog.prompt.llm.LLMProvider
import ai.koog.prompt.llm.LLModel
import com.karastift.erzaehler.domain.model.requests.TopicRequest
import com.karastift.erzaehler.prompts.topicPrompt
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.request.receiveText
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    install(Koog) {
        llm {
            google(apiKey = System.getenv("GOOGLE_API_KEY"))
        }
    }

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            ignoreUnknownKeys = false
            isLenient = false
        })
    }

    routing {
        index()
        generateTopic()
        generateStory()
    }
}