package com.karastift.erzaehler.voice

import kotlinx.serialization.Serializable

@Serializable
data class GenerationConfig(
    val volume: Int = 1,
    val speed: Int = 1,
    val emotion: String = "neutral"
)
