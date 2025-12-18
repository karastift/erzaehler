package com.karastift.erzaehler.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Story(
    val characters: List<StoryCharacter>,
    val script: List<ScriptItem>
)
