package com.karastift.erzaehler.voice

import kotlinx.serialization.Serializable

@Serializable
data class OutputFormat(
    val container: String = "wav",
    val encoding: String = "pcm_s16le",
    val sample_rate: Int = 16000
)