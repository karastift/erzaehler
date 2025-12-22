package com.karastift.erzaehler.ui

import com.karastift.erzaehler.domain.model.entities.Story

data class StoryUIState (
    val topic: String = "",
    val story: Story? = null,
    val isLoading: Boolean = false,
    val isUserSuggestion: Boolean = true, // generated topic should not be passed into suggestion again
    val errorMessage: String = "",
)