package com.karastift.erzaehler.domain.model.entities

import com.karastift.erzaehler.domain.model.valueobjects.CharacterId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("dialog")
data class Dialog(
    val speaker: CharacterId,
    val text: String
) : ScriptItem()


