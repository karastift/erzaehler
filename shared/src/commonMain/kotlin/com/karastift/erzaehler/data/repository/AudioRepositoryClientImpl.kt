package com.karastift.erzaehler.data.repository

import com.karastift.erzaehler.domain.model.entities.AudioData
import com.karastift.erzaehler.domain.model.entities.Dialog
import com.karastift.erzaehler.domain.model.enums.LanguageCode
import com.karastift.erzaehler.domain.model.requests.AudioRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class AudioRepositoryClientImpl(
    private val httpClient: HttpClient
) : AudioRepository {

    override suspend fun getAudioFromDialog(audioRequest: AudioRequest): AudioData {

        try {
            val response = httpClient.post("/voice") {
                setBody(audioRequest)
            }

            val bytes = response.body<ByteArray>()

            return AudioData(bytes)
        }
        catch (e: Exception) {
            throw mapException(e)
        }
    }
}