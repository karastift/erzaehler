package com.karastift.erzaehler.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class StoryCharacter(
    val id: String,
    val name: String
)
