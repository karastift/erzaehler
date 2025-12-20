package com.karastift.erzaehler.domain.model.responses

import kotlinx.serialization.Serializable

@Serializable
data class TopicResponse(
    val topic: String
)