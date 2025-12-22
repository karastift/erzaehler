package com.karastift.erzaehler.di

import com.karastift.erzaehler.Constants
import com.karastift.erzaehler.data.repository.StoryRepository
import com.karastift.erzaehler.data.repository.StoryRepositoryClientImpl
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

    factory {
        StoryViewModel(
            generateTopic = get(),
            generateStory = get()
        )
    }
}
