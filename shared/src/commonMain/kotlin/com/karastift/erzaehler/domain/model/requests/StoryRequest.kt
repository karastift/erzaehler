package com.karastift.erzaehler.domain.model.requests

import com.karastift.erzaehler.domain.model.enums.LanguageCode
import com.karastift.erzaehler.domain.model.enums.LanguageLevel
import kotlinx.serialization.Serializable

@Serializable
data class StoryRequest(
    val languageCode: LanguageCode,
    val languageLevel: LanguageLevel,
    val topic: String,
)