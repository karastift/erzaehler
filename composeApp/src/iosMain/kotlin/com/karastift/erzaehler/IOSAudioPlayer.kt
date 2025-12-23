package com.karastift.erzaehler

import com.karastift.erzaehler.audio.AudioPlayer
import com.karastift.erzaehler.domain.model.entities.AudioData
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSData
import platform.Foundation.create

class IOSAudioPlayer : AudioPlayer {

    private var player: AVAudioPlayer? = null

    @OptIn(ExperimentalForeignApi::class)
    override fun load(audio: AudioData) {
        audio.bytes.usePinned { pinned ->
            val data = NSData.create(
                bytes = pinned.addressOf(0),
                length = audio.bytes.size.toULong()
            )

            player = AVAudioPlayer(data = data, error = null)
            player?.prepareToPlay()
        }
    }

    override fun play() {
        player?.play()
    }

    override fun pause() {
        player?.pause()
    }

    override fun stop() {
        player?.stop()
    }

    override fun setVolume(volume: Float) {
        player?.volume = volume
    }
}
