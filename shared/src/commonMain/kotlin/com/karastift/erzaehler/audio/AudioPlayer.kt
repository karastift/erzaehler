package com.karastift.erzaehler.audio

import com.karastift.erzaehler.domain.model.entities.AudioData

interface AudioPlayer {
    suspend fun play(audioData: AudioData)
    fun pause()
    fun stop()
    fun setVolume(volume: Float)
}
