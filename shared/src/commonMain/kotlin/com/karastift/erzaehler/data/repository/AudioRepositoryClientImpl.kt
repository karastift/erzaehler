package com.karastift.erzaehler.data.repository

import com.karastift.erzaehler.domain.model.entities.AudioData
import com.karastift.erzaehler.domain.model.requests.VoiceRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class AudioRepositoryClientImpl(
    private val httpClient: HttpClient
) : AudioRepository {

    override suspend fun getAudioFromDialog(voiceRequest: VoiceRequest): AudioData {

        try {
            val response = httpClient.post("/voice") {
                setBody(voiceRequest)
            }

            val bytes = response.body<ByteArray>()

            return AudioData(bytes)
        }
        catch (e: Exception) {
            throw mapException(e)
        }
    }
}