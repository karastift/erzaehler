package com.karastift.erzaehler.domain.model.entities

import com.karastift.erzaehler.domain.model.enums.CharacterId
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: CharacterId,
    val name: String
)
