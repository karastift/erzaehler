package com.karastift.erzaehler.domain.model.entities

import com.karastift.erzaehler.domain.model.enums.CharacterId
import com.karastift.erzaehler.domain.model.enums.LanguageCode
import com.karastift.erzaehler.domain.model.enums.LanguageLevel
import kotlinx.serialization.Serializable

@Serializable
data class Story(
    val languageCode: LanguageCode,
    val languageLevel: LanguageLevel,
    val characters: List<Character>,
    val voiceAssignments: Map<CharacterId, String>,
    val script: List<ScriptItem>,
)
