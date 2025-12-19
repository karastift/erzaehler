package com.karastift.erzaehler.data.repository

import com.karastift.erzaehler.domain.model.enums.LanguageCode
import com.karastift.erzaehler.domain.model.enums.LanguageLevel
import com.karastift.erzaehler.domain.model.entities.Story

expect interface StoryRepository {

    suspend fun getTopic(
        suggestion: String?,
        languageCode: LanguageCode,
        languageLevel: LanguageLevel,
    ): String

    suspend fun getStory(
        topic: String,
        language: LanguageCode,
        languageLevel: LanguageLevel,
    ): Story
}