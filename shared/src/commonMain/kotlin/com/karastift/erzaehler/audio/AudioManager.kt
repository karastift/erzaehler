package com.karastift.erzaehler.audio

import com.karastift.erzaehler.domain.model.entities.AudioData
import com.karastift.erzaehler.domain.model.requests.AudioRequest
import com.karastift.erzaehler.domain.usecase.GenerateAudioUseCase

class AudioManager(
    private val generateAudio: GenerateAudioUseCase,
    private val audioPlayer: AudioPlayer,
    private val cache: AudioCache,
) {

    suspend fun ensureAudioLoaded(request: AudioRequest) {
        val key = cacheKey(request)

        if (cache.get(key) == null) {
            val audio = generateAudio(request)
            cache.put(key, audio)
        }
    }

    suspend fun playAndWait(request: AudioRequest) {
        val audio = getOrLoad(request)
        audioPlayer.play(audio)
    }

    fun stop() {
        audioPlayer.stop()
    }

    private suspend fun getOrLoad(request: AudioRequest): AudioData {
        val key = cacheKey(request)

        return cache.get(key)
            ?: generateAudio(request).also { cache.put(key, it) }
    }

    private fun cacheKey(audioRequest: AudioRequest): String {
        return "${audioRequest.languageCode}_${audioRequest.languageLevel}_${audioRequest.dialog.text.hashCode()}"
    }
}