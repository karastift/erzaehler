package com.karastift.erzaehler.domain.model.responses

import com.karastift.erzaehler.domain.model.entities.Story
import kotlinx.serialization.Serializable

@Serializable
data class StoryResponse(
    val story: Story
)