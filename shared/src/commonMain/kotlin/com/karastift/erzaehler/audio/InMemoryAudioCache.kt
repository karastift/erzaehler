package com.karastift.erzaehler.audio

import com.karastift.erzaehler.domain.model.entities.AudioData

class InMemoryAudioCache : AudioCache {
    private val cache = mutableMapOf<String, AudioData>()

    override fun get(key: String): AudioData? = cache[key]

    override fun put(key: String, audio: AudioData) {
        cache[key] = audio
    }

    override fun clear() {
        cache.clear()
    }
}