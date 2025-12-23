package com.karastift.erzaehler

import com.karastift.erzaehler.audio.AudioPlayer
import com.karastift.erzaehler.domain.model.entities.AudioData
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.AVFAudio.AVAudioPlayer
import platform.AVFAudio.AVAudioPlayerDelegateProtocol
import platform.Foundation.NSData
import platform.Foundation.create
import platform.darwin.NSObject
import kotlin.coroutines.resume

class IOSAudioPlayer : AudioPlayer {

    private var player: AVAudioPlayer? = null
    private var delegate: AVAudioPlayerDelegateProtocol? = null

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun play(audioData: AudioData) =
        suspendCancellableCoroutine { cont ->

            audioData.bytes.usePinned { pinned ->
                val data = NSData.create(
                    bytes = pinned.addressOf(0),
                    length = audioData.bytes.size.toULong()
                )

                player = AVAudioPlayer(data = data, error = null)
            }

            delegate = object : NSObject(), AVAudioPlayerDelegateProtocol {
                override fun audioPlayerDidFinishPlaying(
                    player: AVAudioPlayer,
                    successfully: Boolean
                ) {
                    if (cont.isActive) {
                        cont.resume(Unit)
                    }
                }
            }

            player?.delegate = delegate
            player?.prepareToPlay()
            player?.play()

            cont.invokeOnCancellation {
                player?.stop()
            }
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
