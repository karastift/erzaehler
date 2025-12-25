package com.karastift.erzaehler.audio

import com.karastift.erzaehler.domain.model.entities.AudioData
import com.karastift.erzaehler.domain.model.requests.VoiceRequest
import com.karastift.erzaehler.domain.usecase.GenerateAudioUseCase

class AudioManager(
    private val generateAudio: GenerateAudioUseCase,
    private val audioPlayer: AudioPlayer,
    private val cache: AudioCache,
) {

    suspend fun ensureAudioLoaded(request: VoiceRequest) {
        val key = cacheKey(request)

        if (cache.get(key) == null) {
            val audio = generateAudio(request)
            cache.put(key, audio)
        }
    }

    suspend fun playAndWait(request: VoiceRequest) {
        val audio = getOrLoad(request)
        audioPlayer.play(audio)
    }

    fun stop() {
        audioPlayer.stop()
    }

    private suspend fun getOrLoad(request: VoiceRequest): AudioData {
        val key = cacheKey(request)

        return cache.get(key)
            ?: generateAudio(request).also { cache.put(key, it) }
    }

    private fun cacheKey(voiceRequest: VoiceRequest): String {
        return "${voiceRequest.languageCode}_${voiceRequest.languageLevel}_${voiceRequest.dialog.text.hashCode()}"
    }
}