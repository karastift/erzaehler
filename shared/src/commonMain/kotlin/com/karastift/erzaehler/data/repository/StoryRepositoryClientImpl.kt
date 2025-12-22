package com.karastift.erzaehler.data.repository

import com.karastift.erzaehler.domain.model.enums.LanguageCode
import com.karastift.erzaehler.domain.model.enums.LanguageLevel
import com.karastift.erzaehler.domain.model.requests.StoryRequest
import com.karastift.erzaehler.domain.model.requests.TopicRequest
import com.karastift.erzaehler.domain.model.responses.StoryResponse
import com.karastift.erzaehler.domain.model.responses.TopicResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.io.IOException

class StoryRepositoryClientImpl(
    private val httpClient: HttpClient,
) : StoryRepository {

    override
    suspend fun getTopic(
        languageCode: LanguageCode,
        languageLevel: LanguageLevel,
        suggestion: String
    ): TopicResponse {

        val body = TopicRequest(
            languageCode = languageCode,
            languageLevel = languageLevel,
            suggestion = suggestion
        )

        try {
            val response = httpClient.post("/topic") {
                setBody(body)
            }
            return response.body<TopicResponse>()
        }
        catch (e: Exception) {
            throw mapException(e)
        }
    }

    override suspend fun getStory(
        languageCode: LanguageCode,
        languageLevel: LanguageLevel,
        topic: String,
    ): StoryResponse {

        val body = StoryRequest(
            languageCode = languageCode,
            languageLevel = languageLevel,
            topic = topic,
        )

        try {
            val response = httpClient.post("/story") {
                setBody(body)
            }
            return response.body<StoryResponse>()
        }
        catch (e: Exception) {
            throw mapException(e)
        }
    }
}