package com.karastift.erzaehler.voice

import com.karastift.erzaehler.domain.model.entities.Voice
import com.karastift.erzaehler.domain.model.enums.LanguageCode
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.http.ContentType
import io.ktor.client.request.*
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json


class CartesiaVoiceProvider(
    private val httpClient: HttpClient,
    private val token: String,
    private val cartesiaVersion: String
) {
    suspend fun generateVoice(
        text: String,
        voiceId: String,
        modelId: String,
        languageCode: LanguageCode,
        generationConfig: GenerationConfig = GenerationConfig(),
    ): ByteArray {

        val body = CartesiaRequest(
            model_id = modelId,
            transcript = text,
            voice = VoiceSpec(id = voiceId),
            output_format = OutputFormat(),
            language = languageCode.toString().lowercase(),
            generation_config = generationConfig,
        )

        // TODO: Remove log

        val json = Json {
            ignoreUnknownKeys = true // to not crash if cartesia api includes more fields
            encodeDefaults = true // need defaults for example from OutputFormat
            prettyPrint = true
        }

        println(json.encodeToString(body))

        // return ByteArray(0)

        return httpClient.post("https://api.cartesia.ai/tts/bytes") {
            header("Authorization", "Bearer $token")
            header("Cartesia-Version", cartesiaVersion)
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body<ByteArray>()
    }

    suspend fun listVoices(languageCode: LanguageCode, limit: Int = 25): List<Voice> {
        val response = httpClient.get("https://api.cartesia.ai/voices") {
            headers {
                append("Authorization", "Bearer $token")
                append("Cartesia-Version", cartesiaVersion)
            }
            url {
                parameters.append("limit", limit.toString())
                parameters.append("language", languageCode.toString().lowercase())
            }
        }

        if (response.status != HttpStatusCode.OK) {
            throw Exception("Failed to list voices: ${response.status}")
        }

        val voicesResponse: VoicesResponse = response.body()
        return voicesResponse.data
    }
}