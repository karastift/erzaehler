package com.karastift.erzaehler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.karastift.erzaehler.audio.AudioPlayer
import com.karastift.erzaehler.di.appModule
import com.karastift.erzaehler.domain.model.entities.AudioData
import org.koin.compose.KoinApplication
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val audioPlayer = AndroidAudioPlayer(applicationContext)
        setContent {
            KoinApplication(
                application = {
                    modules(
                        appModule,
                        module {
                            single<AudioPlayer> { audioPlayer }
                        }
                    )
                }
            ) {
                App()
            }
        }
    }
}

// Fake implementation for previews (doesn't require context)
class FakeAudioPlayer : AudioPlayer {
    override suspend fun play(audioData: AudioData) {}
    override fun pause() {}
    override fun stop() {}
    override fun setVolume(volume: Float) {}
}

@Preview
@Composable
fun AppAndroidPreview() {
    KoinApplication(
        application = {
            modules(
                appModule,
                module {
                    single<AudioPlayer> { FakeAudioPlayer() }
                }
            )
        }
    ) {
        App()
    }
}