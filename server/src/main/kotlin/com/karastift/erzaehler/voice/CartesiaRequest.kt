package com.karastift.erzaehler.voice

import kotlinx.serialization.Serializable

@Serializable
data class CartesiaRequest(
    val model_id: String,
    val transcript: String,
    val voice: VoiceSpec,
    val output_format: OutputFormat,
    val language: String,
    val generation_config: GenerationConfig,
    val save: Boolean = false,
    val pronunciation_dict_id: String? = null,
//    val speed: String = "normal" // is deprecated
)