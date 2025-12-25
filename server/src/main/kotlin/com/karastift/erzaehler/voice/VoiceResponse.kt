package com.karastift.erzaehler.voice

import com.karastift.erzaehler.domain.model.entities.Voice
import kotlinx.serialization.Serializable

@Serializable
data class VoicesResponse(
    val data: List<Voice>,
    val has_more: Boolean,
    val next_cursor: String? = null
)
