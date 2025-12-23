package com.karastift.erzaehler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.karastift.erzaehler.audio.AudioPlayer
import com.karastift.erzaehler.di.appModule
import org.koin.compose.KoinApplication
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val audioPlayer = AndroidAudioPlayer()
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

@Preview
@Composable
fun AppAndroidPreview() {
    KoinApplication(
        application = {
            modules(
                appModule,
                module {
                    single<AudioPlayer> { AndroidAudioPlayer() }
                }
            )
        }
    ) {
        App()
    }
}