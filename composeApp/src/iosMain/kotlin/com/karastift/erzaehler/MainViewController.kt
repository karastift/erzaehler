package com.karastift.erzaehler

import androidx.compose.ui.window.ComposeUIViewController
import com.karastift.erzaehler.audio.AudioPlayer
import com.karastift.erzaehler.di.appModule
import org.koin.compose.KoinApplication
import org.koin.dsl.module

fun MainViewController() = ComposeUIViewController {
    val audioPlayer = IOSAudioPlayer()

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