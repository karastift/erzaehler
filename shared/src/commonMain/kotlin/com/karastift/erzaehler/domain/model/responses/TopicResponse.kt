package com.karastift.erzaehler.domain.model.responses

import com.karastift.erzaehler.domain.model.entities.Topic
import kotlinx.serialization.Serializable

@Serializable
data class TopicResponse(
    val topic: Topic
)
