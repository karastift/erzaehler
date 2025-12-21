package com.karastift.erzaehler.domain.model.requests

import com.karastift.erzaehler.domain.model.enums.LanguageCode
import com.karastift.erzaehler.domain.model.enums.LanguageLevel
import kotlinx.serialization.Serializable

@Serializable
data class TopicRequest(
    val languageCode: LanguageCode,
    val languageLevel: LanguageLevel,
    val suggestion: String,
)