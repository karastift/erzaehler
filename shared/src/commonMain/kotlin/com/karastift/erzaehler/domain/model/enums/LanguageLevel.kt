package com.karastift.erzaehler.domain.model.enums

import kotlinx.serialization.Serializable

@Serializable
enum class LanguageLevel {
    BEGINNER,
    ELEMENTARY,
    INTERMEDIATE,
    UPPER_INTERMEDIATE,
    ADVANCED,
    PROFICIENT,
}

fun LanguageLevel.toSpeechSpeed(): Float =
    when (this) {
        LanguageLevel.BEGINNER -> 0.85f
        LanguageLevel.ELEMENTARY -> 0.9f
        LanguageLevel.INTERMEDIATE -> 1.0f
        LanguageLevel.UPPER_INTERMEDIATE -> 1.0f
        LanguageLevel.ADVANCED -> 1.0f
        LanguageLevel.PROFICIENT -> 1.0f
    }