package com.karastift.erzaehler.audio

import com.karastift.erzaehler.domain.model.requests.AudioRequest
import com.karastift.erzaehler.domain.usecase.GenerateAudioUseCase

class AudioManager(
    val generateAudio: GenerateAudioUseCase,
    val audioPlayer: AudioPlayer,
    val cache: AudioCache,
) {
    suspend fun ensureAudioLoaded(audioRequest: AudioRequest) {
        val cacheKey = cacheKey(audioRequest)

        val cachedAudio = cache.get(cacheKey)

        if (cachedAudio == null) {

            // TODO: error handling for this somewhere
            val audioData = generateAudio(audioRequest)
            cache.put(cacheKey, audioData)
        }
    }

    suspend fun ensureAudioAndPlay(audioRequest: AudioRequest) {
        ensureAudioLoaded(audioRequest)
        play(audioRequest)
    }

    private fun play(audioRequest: AudioRequest) {
        val audio = cache.get(cacheKey(audioRequest))

        audio?.let { audio ->
            audioPlayer.load(audio)
            audioPlayer.play()
        }
    }

    private fun cacheKey(audioRequest: AudioRequest): String {
        return "${audioRequest.languageCode}_${audioRequest.languageLevel}_${audioRequest.dialog.text.hashCode()}"
    }
}