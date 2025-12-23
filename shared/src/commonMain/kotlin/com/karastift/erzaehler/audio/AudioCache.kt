package com.karastift.erzaehler.audio

import com.karastift.erzaehler.domain.model.entities.AudioData

interface AudioCache {
    fun get(key: String): AudioData?
    fun put(key: String, audio: AudioData)

    fun clear()
}