package com.karastift.erzaehler.ui

import com.karastift.erzaehler.domain.model.entities.Story
import com.karastift.erzaehler.domain.model.enums.LanguageCode
import com.karastift.erzaehler.domain.model.enums.LanguageLevel

data class StoryUIState (
    val language: LanguageCode = LanguageCode.DE,
    val languageLevel: LanguageLevel = LanguageLevel.INTERMEDIATE,
    val topic: String = "",
    val story: Story? = null,
    val isLoading: Boolean = false,
    val isUserSuggestion: Boolean = true, // a generated topic should not be passed into suggestion again
    val errorMessage: String = "",
)