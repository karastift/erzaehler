package com.karastift.erzaehler.ui

data class StoryUIState (
    val topic: String = "",
    val storyJson: String = "",
    val isLoading: Boolean = false
)