package com.karastift.erzaehler.domain.usecase

import com.karastift.erzaehler.data.repository.StoryRepository
import com.karastift.erzaehler.domain.model.enums.LanguageCode
import com.karastift.erzaehler.domain.model.enums.LanguageLevel
import com.karastift.erzaehler.domain.model.responses.StoryResponse
import com.karastift.erzaehler.domain.model.responses.TopicResponse

class GenerateTopicUseCase(
    private val repository: StoryRepository
) {
    suspend operator fun invoke(
        languageCode: LanguageCode,
        languageLevel: LanguageLevel,
        suggestion: String?,
    ): TopicResponse {

        return repository.getTopic(
            languageCode = languageCode,
            languageLevel = languageLevel,
            suggestion = suggestion,
        )
    }
}