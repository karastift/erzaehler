package com.karastift.erzaehler.voice

import com.karastift.erzaehler.domain.model.enums.Emotion
import kotlinx.serialization.Serializable

@Serializable
data class GenerationConfig(
    /** [0.5, 2.0] inclusive **/
    val volume: Float = 1f,
    /** [0.6, 1.5] inclusive **/
    val speed: Float = 1f,
    val emotion: Emotion = Emotion.NEUTRAL
)
