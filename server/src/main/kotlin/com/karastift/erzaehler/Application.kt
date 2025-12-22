package com.karastift.erzaehler

import ai.koog.ktor.Koog
import com.karastift.erzaehler.Constants.SERVER_PORT
import com.karastift.erzaehler.domain.model.enums.LanguageCode
import com.karastift.erzaehler.voice.CartesiaVoiceProvider
import io.ktor.client.HttpClient
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.client.engine.cio.CIO
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.di.dependencies
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

    val httpClient = HttpClient(CIO) {
        install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
            json()
        }
    }

    dependencies {
        provide {
            CartesiaVoiceProvider(
                httpClient = httpClient,
                token = System.getenv("CARTESIA_API_KEY"),
                cartesiaVersion = "2025-04-16",
            )
        }
    }

    routing {
        index()
        generateTopic()
        generateStory()
        post("/voice") {
            val voiceProvier: CartesiaVoiceProvider by dependencies

            val bytes = voiceProvier.generateVoice(
                text = "Was geeeeeeeeehhhttt?? Ich habe heute echt gar kein Bock was zu machen. Hast du Lust vorbeizukommen?",
                voiceId = "b7187e84-fe22-4344-ba4a-bc013fcb533e",
                modelId = "sonic-3",
                languageCode = LanguageCode.EN,
            )

            call.respondBytes(bytes)
        }
    }
}