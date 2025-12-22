package com.karastift.erzaehler.voice

import com.karastift.erzaehler.domain.model.enums.LanguageCode
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.http.ContentType
import io.ktor.client.request.*
import io.ktor.http.contentType


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
    ): ByteArray {

        val body = CartesiaRequest(
            model_id = modelId,
            transcript = text,
            voice = VoiceSpec(id = voiceId),
            output_format = OutputFormat(),
            language = languageCode.toString().lowercase(),
            generation_config = GenerationConfig()
        )

        return httpClient.post("https://api.cartesia.ai/tts/bytes") {
            header("Authorization", "Bearer $token")
            header("Cartesia-Version", cartesiaVersion)
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()
    }
}