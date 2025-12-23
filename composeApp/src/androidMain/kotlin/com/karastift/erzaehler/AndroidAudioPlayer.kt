package com.karastift.erzaehler

import android.content.Context
import android.media.MediaPlayer
import com.karastift.erzaehler.audio.AudioPlayer
import com.karastift.erzaehler.domain.model.entities.AudioData
import java.io.File

class AndroidAudioPlayer(
    private val context: Context
) : AudioPlayer {

    private var player: MediaPlayer? = null
    private var tempFile: File? = null

    override suspend fun play(audioData: AudioData) {
        player?.start()
    }

    override fun pause() {
        player?.pause()
    }

    override fun stop() {
        player?.stop()
    }

    override fun setVolume(volume: Float) {
        player?.setVolume(volume, volume)
    }

    private fun release() {
        player?.release()
        player = null

        tempFile?.delete()
        tempFile = null
    }
}