package com.karastift.erzaehler.domain.usecase

import com.karastift.erzaehler.data.repository.StoryRepository
import com.karastift.erzaehler.domain.model.enums.LanguageCode
import com.karastift.erzaehler.domain.model.enums.LanguageLevel
import com.karastift.erzaehler.domain.model.responses.StoryResponse

class GenerateStoryUseCase(
    private val repository: StoryRepository
) {
    suspend operator fun invoke(
        topic: String,
        languageCode: LanguageCode,
        languageLevel: LanguageLevel,
    ): StoryResponse {

        return repository.getStory(topic, languageCode, languageLevel)
    }
}