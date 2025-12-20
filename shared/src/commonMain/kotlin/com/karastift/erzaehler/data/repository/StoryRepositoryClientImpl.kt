package com.karastift.erzaehler.data.repository

import com.karastift.erzaehler.domain.model.enums.LanguageCode
import com.karastift.erzaehler.domain.model.enums.LanguageLevel
import com.karastift.erzaehler.domain.model.responses.StoryResponse
import com.karastift.erzaehler.domain.model.responses.TopicResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlin.random.Random

class StoryRepositoryClientImpl(
    private val httpClient: HttpClient,
) : StoryRepository {

    override
    suspend fun getTopic(
        languageCode: LanguageCode,
        languageLevel: LanguageLevel,
        suggestion: String?
    ): TopicResponse {

        val res = httpClient.get("https://httpbin.org/get").bodyAsText()
        println(res)

        val list = listOf(
            "Interaktion im an der Selbstbedienungskasse bei Rewe",
            "Wer aus der WG hat meinen Aufstrich aufgebraucht??",
            "Studium ist bald vorbei und eigentlich will ich noch gar nicht arbeiten"
        )
        val randomIndex: Int = Random.nextInt(list.size)

        val topic =  list[randomIndex]

        return TopicResponse(topic = topic)
    }

    override suspend fun getStory(
        topic: String,
        language: LanguageCode,
        languageLevel: LanguageLevel
    ): StoryResponse {

        val storyString =  """
        {
          "characters": [
            { "id": "policeman", "name": "policeman" },
            { "id": "businessman", "name": "businessman" }
          ],
          "script": [
            { "type": "enter", "id": "policeman" },
            { "type": "enter", "id": "businessman" },
            { "type": "dialog", "speaker": "policeman", "text": "Hello." },
            { "type": "dialog", "speaker": "businessman", "text": "Oh, hi!" },
            { "type": "dialog", "speaker": "policeman", "text": "Nice evening, isn't it?" },
            { "type": "exit", "id": "businessman" },
            { "type": "dialog", "speaker": "policeman", "text": "Wait, where did you go?" },
            { "type": "enter", "id": "businessman" },
            { "type": "dialog", "speaker": "businessman", "text": "Hallo ich bin wieder da" }
          ]
        }
        """

//        return StoryResponse(story = )
        TODO("not implemented")
    }
}