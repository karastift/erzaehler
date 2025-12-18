package com.karastift.erzaehler.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("enter")
data class Enter(
    val id: String
) : ScriptItem()
