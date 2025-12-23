package com.karastift.erzaehler.di

import com.karastift.erzaehler.Constants
import com.karastift.erzaehler.audio.AudioCache
import com.karastift.erzaehler.audio.AudioManager
import com.karastift.erzaehler.audio.AudioPlayer
import com.karastift.erzaehler.audio.InMemoryAudioCache
import com.karastift.erzaehler.data.repository.AudioRepository
import com.karastift.erzaehler.data.repository.AudioRepositoryClientImpl
import com.karastift.erzaehler.data.repository.StoryRepository
import com.karastift.erzaehler.data.repository.StoryRepositoryClientImpl
import com.karastift.erzaehler.domain.usecase.GenerateAudioUseCase
import com.karastift.erzaehler.domain.usecase.GenerateStoryUseCase
import com.karastift.erzaehler.domain.usecase.GenerateTopicUseCase
import com.karastift.erzaehler.ui.StoryViewModel
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val appModule = module {

    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = false
                })
            }
            defaultRequest {
                url(Constants.BASE_URL)
                contentType(ContentType.Application.Json)
            }
        }
    }

    single<StoryRepository> {
        StoryRepositoryClientImpl(
            httpClient = get()
        )
    }

    single<AudioRepository> {
        AudioRepositoryClientImpl(
            httpClient = get()
        )
    }

    single<GenerateTopicUseCase> {
        GenerateTopicUseCase(
            repository = get()
        )
    }

    single<GenerateStoryUseCase> {
        GenerateStoryUseCase(
            repository = get()
        )
    }

    single<GenerateAudioUseCase> {
        GenerateAudioUseCase(
            repository = get()
        )
    }

    single<AudioCache> { InMemoryAudioCache() }

    single<AudioManager> {
        AudioManager(
            generateAudio = get(),
            audioPlayer = get(),
            cache = get(),
        )
    }

    factory {
        StoryViewModel(
            generateTopic = get(),
            generateStory = get()
        )
    }
}
