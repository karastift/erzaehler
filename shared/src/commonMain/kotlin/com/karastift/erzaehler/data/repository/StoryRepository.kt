package com.karastift.erzaehler.data.repository

import com.karastift.erzaehler.domain.model.enums.LanguageCode
import com.karastift.erzaehler.domain.model.enums.LanguageLevel
import com.karastift.erzaehler.domain.model.entities.Story
import com.karastift.erzaehler.domain.model.responses.StoryResponse
import com.karastift.erzaehler.domain.model.responses.TopicResponse

interface StoryRepository {

    suspend fun getTopic(
        languageCode: LanguageCode,
        languageLevel: LanguageLevel,
        suggestion: String?,
    ): TopicResponse

    suspend fun getStory(
        topic: String,
        language: LanguageCode,
        languageLevel: LanguageLevel,
    ): StoryResponse
}