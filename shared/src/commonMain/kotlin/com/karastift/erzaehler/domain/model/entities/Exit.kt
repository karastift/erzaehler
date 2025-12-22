package com.karastift.erzaehler.domain.model.entities

import com.karastift.erzaehler.domain.model.enums.CharacterId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("exit")
data class Exit(
    val id: CharacterId
) : ScriptItem()
