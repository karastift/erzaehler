package com.karastift.erzaehler.audio

import com.karastift.erzaehler.domain.model.entities.AudioData

interface AudioPlayer {
    fun load(audio: AudioData)
    fun play()
    fun pause()
    fun stop()
    fun setVolume(volume: Float)
}
