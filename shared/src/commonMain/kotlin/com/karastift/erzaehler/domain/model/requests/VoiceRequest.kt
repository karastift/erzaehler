package com.karastift.erzaehler.domain.model.requests

import com.karastift.erzaehler.domain.model.entities.Dialog
import com.karastift.erzaehler.domain.model.enums.LanguageCode
import com.karastift.erzaehler.domain.model.enums.LanguageLevel
import kotlinx.serialization.Serializable

@Serializable
data class VoiceRequest(
    val languageCode: LanguageCode,
    val languageLevel: LanguageLevel,
    val dialog: Dialog,
    val voiceId: String,
)