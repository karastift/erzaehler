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