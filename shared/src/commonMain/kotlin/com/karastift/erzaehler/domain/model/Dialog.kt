package com.karastift.erzaehler.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("dialog")
data class Dialog(
    val speaker: String,
    val text: String
) : ScriptItem()


