package com.karastift.erzaehler.voice

import kotlinx.serialization.Serializable

@Serializable
data class VoiceSpec(
    val mode: String = "id",
    val id: String
)